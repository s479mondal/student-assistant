package com.studentassistant.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarksResponse {
    private String subject;
    private int marks;
}
