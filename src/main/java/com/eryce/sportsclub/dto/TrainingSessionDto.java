package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.TrainingSession;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSessionDto {

    private Integer id;
    private LocalDate dateHeld;
    private LocalTime timeHeld;
    private MemberGroupDto memberGroupDto;
    private PeriodDto period;
    private String dayOfWeek;

    public TrainingSession convertToEntity() {
        return TrainingSession.builder()
                .dateHeld(dateHeld)
                .dayOfWeek(dayOfWeek)
                .id(id)
                .memberGroup(memberGroupDto.convertToEntity())
                .period(period.convertToEntity())
                .timeHeld(timeHeld)
                .build();
    }
}
