package com.studentassistant.dto;

import lombok.Data;

@Data
public class CreateAssignmentRequest {

    private Long studentId;
    private Long subjectId;
    private String title;
    private String description;
    private String dueDate; // yyyy-MM-dd
}
