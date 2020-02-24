package com.eryce.sportsclub.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainingSessionId;

    private Integer month;
    private Integer year;

    private LocalDate dateHeld;
    private LocalTime timeHeld;

    public TrainingSession()
    {

    }

    public TrainingSession(Integer id, LocalDate dateHeld, LocalTime timeHeld) {
        this.trainingSessionId=id;
        this.dateHeld = dateHeld;
        this.timeHeld = timeHeld;
        this.month=dateHeld.getMonthValue();
        this.year=dateHeld.getYear();
    }

    public Integer getTrainingSessionId() {
        return trainingSessionId;
    }

    public void setTrainingSessionId(Integer trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
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
    }

    public LocalTime getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(LocalTime timeHeld) {
        this.timeHeld = timeHeld;
    }
}
