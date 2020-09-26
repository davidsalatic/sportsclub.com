package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.MembershipDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    private Integer price;

    public MembershipDto convertToDto() {
        return MembershipDto.builder()
                .id(id)
                .price(price)
                .period(period.convertToDto())
                .build();
    }
}
