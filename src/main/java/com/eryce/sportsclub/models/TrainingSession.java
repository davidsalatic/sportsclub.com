package com.eryce.sportsclub.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer month;
    private Integer year;

    private LocalDate dateHeld;
    private LocalTime timeHeld;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id", nullable = false)
    private MemberGroup memberGroup;

    public TrainingSession()
    {

    }

    public Integer getId() {
        return id;
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

    public LocalDate getDateHeld() {
        return dateHeld;
    }

    public void setDateHeld(LocalDate dateHeld) {
        this.dateHeld = dateHeld;
        this.year = dateHeld.getYear();
        this.month=dateHeld.getMonthValue();
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

    public LocalTime getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(LocalTime timeHeld) {
        this.timeHeld = timeHeld;
    }
}
