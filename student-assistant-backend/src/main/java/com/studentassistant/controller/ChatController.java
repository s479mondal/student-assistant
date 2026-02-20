package com.studentassistant.controller;

import com.studentassistant.dto.ChatMessage;
import com.studentassistant.entity.Message;
import com.studentassistant.entity.User;
import com.studentassistant.repository.MessageRepository;
import com.studentassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage chatMessage) {

        User sender = userRepository.findByUsername(chatMessage.getSenderUsername())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUsername(chatMessage.getReceiverUsername())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(chatMessage.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);

        messageRepository.save(message);

        // 🔥 Send to receiver topic
        messagingTemplate.convertAndSend(
                "/topic/messages/" + receiver.getUsername(),
                chatMessage
        );
    }
}