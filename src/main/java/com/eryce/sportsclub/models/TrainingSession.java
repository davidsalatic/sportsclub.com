package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.TrainingSessionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "M/d/yyyy")
    private LocalDate dateHeld;

    private LocalTime timeHeld;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id")
    private Period period;

    private String dayOfWeek;

    public void setDateHeld(LocalDate dateHeld) {
        this.dateHeld = dateHeld;
        this.dayOfWeek = dateHeld.getDayOfWeek().toString();
    }

    public TrainingSessionDto convertToDto() {
        return TrainingSessionDto.builder()
                .dateHeld(dateHeld)
                .dayOfWeek(dayOfWeek)
                .id(id)
                .memberGroupDto(memberGroup.convertToDto())
                .period(period.convertToDto())
                .timeHeld(timeHeld)
                .build();
    }
}
