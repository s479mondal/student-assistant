package com.studentassistant.dto;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private Long userId;
    private String department;
    private int semester;
}