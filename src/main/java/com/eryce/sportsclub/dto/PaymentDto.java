package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Payment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Integer id;
    private Integer amount;
    private Integer monthOfPayment;
    private Integer dayOfMonth;
    private Integer yearOfPayment;
    private AppUserDto appUser;
    private MembershipDto membership;

    public Payment convertToEntity() {
        return Payment.builder()
                .amount(amount)
                .appUser(appUser.convertToEntity())
                .dayOfMonth(dayOfMonth)
                .id(id)
                .membership(membership.convertToEntity())
                .monthOfPayment(monthOfPayment)
                .yearOfPayment(yearOfPayment)
                .build();
    }
}
