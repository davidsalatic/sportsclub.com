package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Competition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionDto {

    private Integer id;
    private String name;
    private LocalDate dateHeld;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeHeld;
    private String location;
    private String description;

    public Competition convertToEntity() {
        return Competition.builder()
                .dateHeld(dateHeld)
                .description(description)
                .id(id)
                .location(location)
                .name(name)
                .timeHeld(timeHeld)
                .build();
    }
}
