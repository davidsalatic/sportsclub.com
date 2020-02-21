package com.eryce.sportsclub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attendanceId;

    private boolean present;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "club_member_id", nullable = false)
    @JsonIgnore
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_session_id", nullable = false)
    @JsonIgnore
    private TrainingSession trainingSession;
}
