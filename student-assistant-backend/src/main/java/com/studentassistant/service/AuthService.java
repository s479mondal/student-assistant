package com.studentassistant.service;

import com.studentassistant.dto.LoginRequest;
import com.studentassistant.dto.LoginResponse;
import com.studentassistant.dto.SignupRequest;
import com.studentassistant.entity.User;
import com.studentassistant.repository.UserRepository;
import com.studentassistant.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // ==========================
    // SIGNUP (STUDENT ONLY)
    // ==========================
    public String signup(SignupRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        // 🔒 Force role
        user.setRole("STUDENT");

        userRepository.save(user);

        return "Student registered successfully";
    }

public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid credentials");
    }

    String token = jwtService.generateToken(
            user.getUsername(),
            user.getRole()
    );

    return new LoginResponse(
            token,
            user.getUsername(),
            user.getRole()
    );
}
}
