package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Period;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodDto {

    private Integer id;
    private Integer month;
    private Integer year;
    private boolean notifiedManagersOfMembershipDebts;

    public Period convertToEntity() {
        return Period.builder()
                .id(id)
                .month(month)
                .notifiedManagersOfMembershipDebts(notifiedManagersOfMembershipDebts)
                .year(year)
                .build();
    }
}
