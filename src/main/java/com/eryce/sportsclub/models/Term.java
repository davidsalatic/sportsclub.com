package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.TermDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private Integer durationMinutes;
    private Integer dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_group_id", nullable = false)
    private MemberGroup memberGroup;

    public TermDto convertToDto() {
        return TermDto.builder()
                .dayOfWeek(dayOfWeek)
                .durationMinutes(durationMinutes)
                .memberGroup(memberGroup.convertToDto())
                .id(id)
                .startTime(startTime)
                .build();
    }
}
