package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.models.CompetitionApplication;

public class CompetitionApplicationRequestDTO {

    private Integer id;
    private Competition competition;
    private AppUserRequestDTO appUser;
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public AppUserRequestDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserRequestDTO appUser) {
        this.appUser = appUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompetitionApplication generateCompetitionApplication()
    {
        CompetitionApplication competitionApplication = new CompetitionApplication();
        if(this.id!=null)
            competitionApplication.setId(this.id);
        competitionApplication.setCompetition(this.competition);
        competitionApplication.setMessage(this.message);
        competitionApplication.setAppUser(this.appUser.generateAppUser());
        return competitionApplication;
    }
}
