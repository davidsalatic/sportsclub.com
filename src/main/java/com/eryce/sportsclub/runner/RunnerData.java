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


@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    MemberGroupController memberGroupController;
    @Autowired
    AppUserController appUserController;

    @Override
    public void run(String... args) {
        System.out.println("***************APP STARTED*****************");
    }
}