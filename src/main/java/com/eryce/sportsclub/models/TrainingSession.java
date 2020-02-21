package com.eryce.sportsclub.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer trainingSessionId;

    private LocalDate dateHeld;
    private LocalTime timeHeld;
}
