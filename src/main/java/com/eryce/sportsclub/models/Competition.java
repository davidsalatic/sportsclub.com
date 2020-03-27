package com.eryce.sportsclub.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate dateHeld;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeHeld;
    private String location;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateHeld() {
        return dateHeld;
    }

    public void setDateHeld(LocalDate dateHeld) {
        this.dateHeld = dateHeld;
    }

    public LocalTime getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(LocalTime timeHeld) {
        this.timeHeld = timeHeld;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
