package com.studentassistant.dto;

import lombok.Data;

@Data
public class ChatMessage {

    private String senderUsername;
    private String receiverUsername;
    private String content;
}