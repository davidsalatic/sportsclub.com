package com.eryce.sportsclub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateOfPayment;
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "club_member_id", nullable = true)
    @JsonIgnore
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membership_id", nullable = false)
    @JsonIgnore
    private Membership membership;

    public Integer getId() {
        return id;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public int getAmount() {
        return amount;
    }

    public ClubMember getClubMember() {
        return clubMember;
    }

    public Membership getMembership() {
        return membership;
    }
}
