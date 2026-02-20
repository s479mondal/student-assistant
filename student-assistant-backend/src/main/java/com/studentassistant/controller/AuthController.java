package com.studentassistant.controller;

import com.studentassistant.dto.LoginRequest;
import com.studentassistant.dto.SignupRequest;
import com.studentassistant.service.AuthService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import com.studentassistant.dto.LoginResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

 @PostMapping("/signup")
public String signup(@Valid @RequestBody SignupRequest request) {
    return authService.signup(request);
}

@PostMapping("/login")
public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
}

}
