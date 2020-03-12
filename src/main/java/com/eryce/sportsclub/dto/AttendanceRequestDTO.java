package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.TrainingSession;

public class AttendanceRequestDTO {

    private Integer id;
    private AppUserRequestDTO appUserRequestDTO;
    private TrainingSession trainingSession;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUserRequestDTO getAppUser() {
        return appUserRequestDTO;
    }

    public void setAppUser(AppUserRequestDTO appUserRequestDTO) {
        this.appUserRequestDTO = appUserRequestDTO;
    }

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    public Attendance generateAttendance()
    {
        Attendance attendance = new Attendance();
        if(this.id!=null)
            attendance.setId(this.id);
        attendance.setTrainingSession(this.getTrainingSession());
        attendance.setAppUser(this.appUserRequestDTO.generateAppUser());
        return attendance;
    }
}
