package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.PeriodDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Period {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private Integer month;
    private Integer year;

    //on specific date-of-month, notify managers with the list of users who had not
    //made enough payments for a membership in this period.
    //if true, the managers were already notified in this period and will not be notified anymore.
    private boolean notifiedManagersOfMembershipDebts;

    public PeriodDto convertToDto() {
        return PeriodDto.builder()
                .id(id)
                .month(month)
                .year(year)
                .notifiedManagersOfMembershipDebts(notifiedManagersOfMembershipDebts)
                .build();
    }
}
