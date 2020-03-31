package com.eryce.sportsclub.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer month;
    private Integer year;

    //on specific date-of-month, notify managers with the list of users who had not
    //made enough payments for a membership in this period.
    //if true, the managers were already notified in this period and will not be notified anymore.
    private Boolean notifiedManagersOfMembershipDebts=false;

    public Boolean getNotifiedManagersOfMembershipDebts() {
        return notifiedManagersOfMembershipDebts;
    }

    public void setNotifiedManagersOfMembershipDebts(Boolean notifiedManagersOfMembershipDebts) {
        this.notifiedManagersOfMembershipDebts = notifiedManagersOfMembershipDebts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
