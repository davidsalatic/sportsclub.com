package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Attendance;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {

    private Integer id;
    private AppUserDto appUser;
    private TrainingSessionDto trainingSession;

    public Attendance convertToEntity() {
        return Attendance.builder()
                .id(id)
                .trainingSession(trainingSession.convertToEntity())
                .appUser(appUser.convertToEntity())
                .build();
    }
}
