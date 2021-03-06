package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.CompetitionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate dateHeld;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeHeld;
    private String location;
    private String description;

    public CompetitionDto convertToDto() {
        return CompetitionDto.builder()
                .dateHeld(dateHeld)
                .description(description)
                .location(location)
                .name(name)
                .timeHeld(timeHeld)
                .id(id)
                .build();
    }
}
