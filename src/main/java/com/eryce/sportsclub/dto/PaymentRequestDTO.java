package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;


public class PaymentRequestDTO {

    private Integer id;
    private Integer amount;
    private Integer monthOfPayment;
    private Integer dayOfMonth;
    private Integer yearOfPayment;
    private AppUserRequestDTO appUserRequestDTO;
    private Membership membership;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMonthOfPayment() {
        return monthOfPayment;
    }

    public void setMonthOfPayment(Integer monthOfPayment) {
        this.monthOfPayment = monthOfPayment;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getYearOfPayment() {
        return yearOfPayment;
    }

    public void setYearOfPayment(Integer yearOfPayment) {
        this.yearOfPayment = yearOfPayment;
    }

    public AppUserRequestDTO getAppUser() {
        return appUserRequestDTO;
    }

    public void setAppUser(AppUserRequestDTO appUserRequestDTO) {
        this.appUserRequestDTO = appUserRequestDTO;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Payment generatePayment()
    {
        Payment payment = new Payment();
        payment.setAmount(this.amount);
        payment.setAppUser(this.appUserRequestDTO.generateAppUser());
        payment.setMembership(this.membership);
        payment.setDayOfMonth(this.dayOfMonth);
        payment.setMonthOfPayment(this.monthOfPayment);
        payment.setYearOfPayment(this.yearOfPayment);
        if(this.id!=null)
            payment.setId(this.id);
        return payment;
    }
}
