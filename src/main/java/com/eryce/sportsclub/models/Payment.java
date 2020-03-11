package com.eryce.sportsclub.models;

import javax.persistence.*;

@Entity
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

    public Integer getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
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
}