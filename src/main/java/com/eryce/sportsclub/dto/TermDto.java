package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Term;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermDto {

    private Integer id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private Integer durationMinutes;
    private Integer dayOfWeek;
    private MemberGroupDto memberGroup;

    public Term convertToEntity() {
        return Term.builder()
                .dayOfWeek(dayOfWeek)
                .durationMinutes(durationMinutes)
                .id(id)
                .memberGroup(memberGroup.convertToEntity())
                .startTime(startTime)
                .build();
    }
}
