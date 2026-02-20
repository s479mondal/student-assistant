package com.studentassistant.controller;

import com.studentassistant.entity.Message;
import com.studentassistant.entity.User;
import com.studentassistant.repository.MessageRepository;
import com.studentassistant.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ MARK MESSAGE AS READ
    @PutMapping("/read/{messageId}")
    public Message markAsRead(
            HttpServletRequest request,
            @PathVariable Long messageId) {

        User user = getLoggedInUser(request);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // Only receiver can mark as read
        if (!message.getReceiver().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to mark this message");
        }

        message.setRead(true);

        return messageRepository.save(message);
    }
}