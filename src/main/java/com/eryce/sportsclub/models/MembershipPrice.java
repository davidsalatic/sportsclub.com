package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.MembershipPriceDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPrice {

    @Id
    private Integer id;
    private Integer price;

    public MembershipPriceDto convertToDto() {
        return MembershipPriceDto.builder()
                .id(id)
                .price(price)
                .build();
    }
}
