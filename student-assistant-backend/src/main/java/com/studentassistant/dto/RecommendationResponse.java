package com.studentassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationResponse {

    private String type;      // MARKS / ATTENDANCE
    private String message;   // Recommendation text
}
