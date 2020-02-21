package com.eryce.sportsclub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer membershipId;

    private int month;
    private int year;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "club_member_id", nullable = false)
    @JsonIgnore
    private ClubMember clubMember;
}
