package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.AttendanceDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_session_id", nullable = false)
    private TrainingSession trainingSession;

    public AttendanceDto convertToDto() {
        return AttendanceDto.builder()
                .id(id)
                .trainingSession(trainingSession.convertToDto())
                .appUser(appUser.convertToDto())
                .build();
    }
}