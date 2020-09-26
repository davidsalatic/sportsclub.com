package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Membership;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipDto {

    private Integer id;
    private Integer price;
    private PeriodDto period;

    public Membership convertToEntity() {
        return Membership.builder()
                .id(id)
                .period(period.convertToEntity())
                .price(price)
                .build();
    }
}
