package com.studentassistant.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardResponse {

    private Long studentId;
    private double averageMarks;
    private boolean attendanceWarning;
    private List<String> weakSubjects;
    private List<MarksResponse> marks;
    private List<AttendanceResponse> attendance;
    private List<RecommendationResponse> recommendations;

}
