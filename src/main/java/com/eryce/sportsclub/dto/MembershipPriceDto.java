package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.MembershipPrice;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPriceDto {

    private Integer id;
    private Integer price;

    public MembershipPrice convertToEntity() {
        return MembershipPrice.builder()
                .price(price)
                .id(id)
                .build();
    }
}
