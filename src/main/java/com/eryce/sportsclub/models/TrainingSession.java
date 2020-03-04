package com.eryce.sportsclub.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dateHeld;
    private Integer month;
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id", nullable = false)
    private MemberGroup memberGroup;

    public TrainingSession()
    {

    }

    public LocalDateTime getDateHeld() {
        return dateHeld;
    }

    public void setDateHeld(LocalDateTime dateHeld) {
        this.dateHeld = dateHeld;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(MemberGroup memberGroup) {
        this.memberGroup = memberGroup;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
