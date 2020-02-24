package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.controllers.AppUserController;
import com.eryce.sportsclub.controllers.AttendanceController;
import com.eryce.sportsclub.controllers.MemberGroupController;
import com.eryce.sportsclub.controllers.TrainingSessionController;
import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private MemberGroupController memberGroupController;
    @Autowired
    private AppUserController appUserController;
    @Autowired
    private TrainingSessionController trainingSessionController;
    @Autowired
    private AttendanceController attendanceController;

    @Override
    public void run(String... args) {

        insertTestData();
    }

    private void insertTestData() {

        MemberGroup memberGroup = new MemberGroup(1,"grupa1");
        MemberGroup memberGroup1 = new MemberGroup(2,"grupa2");

        memberGroupController.insertGroup(memberGroup);
        memberGroupController.insertGroup(memberGroup1);

        ClubMember clubMember = new ClubMember("davidsalatic1","sifra123","David","Salatic");
        ClubMember clubMember1 = new ClubMember("dzon1","dzon11","Dzon","Dzonic");

        clubMember.setUserId(1);
        clubMember1.setUserId(2);
        clubMember.setMemberGroup(memberGroup);
        clubMember1.setMemberGroup(memberGroup1);

        appUserController.insertUser(clubMember);
        appUserController.insertUser(clubMember1);

        TrainingSession trainingSession = new TrainingSession(1,LocalDate.now(), LocalTime.now());
        trainingSessionController.insertTrainingSession(trainingSession);

        TrainingSession trainingSession1 = new TrainingSession(2,LocalDate.ofYearDay(2018,200), LocalTime.now());
        trainingSessionController.insertTrainingSession(trainingSession1);

        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setPresent(true);
        attendance.setTrainingSession(trainingSession);
        attendance.setAppUser(clubMember);

        Attendance attendance1 = new Attendance();
        attendance1.setAttendanceId(2);
        attendance1.setPresent(false);
        attendance1.setTrainingSession(trainingSession1);
        attendance1.setAppUser(clubMember);

        attendanceController.insertAttendance(attendance);
        attendanceController.insertAttendance(attendance1);

        System.out.println("Started in Runner");
    }
}