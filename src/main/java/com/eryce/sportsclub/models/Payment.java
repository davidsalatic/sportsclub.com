package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.PaymentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;
    private Integer monthOfPayment;
    private Integer dayOfMonth;
    private Integer yearOfPayment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    public PaymentDto convertToDto() {
        return PaymentDto.builder()
                .amount(amount)
                .appUser(appUser.convertToDto())
                .dayOfMonth(dayOfMonth)
                .id(id)
                .membership(membership.convertToDto())
                .monthOfPayment(monthOfPayment)
                .yearOfPayment(yearOfPayment)
                .build();
    }
}