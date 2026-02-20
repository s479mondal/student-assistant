package com.studentassistant.service;

import com.studentassistant.dto.RecommendationResponse;
import com.studentassistant.entity.Attendance;
import com.studentassistant.entity.Marks;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    public List<RecommendationResponse> generate(
            List<Marks> marksList,
            List<Attendance> attendanceList
    ) {

        List<RecommendationResponse> recommendations = new ArrayList<>();

        // 📉 MARKS BASED RECOMMENDATIONS
        for (Marks m : marksList) {
            if (m.getMarks() < 40) {
                recommendations.add(
                        new RecommendationResponse(
                                "MARKS",
                                "Weak in " + m.getSubject().getName() + ". Focus more."
                        )
                );
            } else if (m.getMarks() < 60) {
                recommendations.add(
                        new RecommendationResponse(
                                "MARKS",
                                "Needs improvement in " + m.getSubject().getName()
                        )
                );
            }
        }

        // 📅 ATTENDANCE BASED RECOMMENDATIONS
        for (Attendance a : attendanceList) {
            if (a.getAttendancePercentage().compareTo(java.math.BigDecimal.valueOf(75)) < 0) {
                recommendations.add(
                        new RecommendationResponse(
                                "ATTENDANCE",
                                "Low attendance in " + a.getSubject().getName()
                        )
                );
            }
        }

        return recommendations;
    }
}
