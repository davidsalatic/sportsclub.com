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

        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setId(1);
        memberGroup.setName("grupa1");

        ClubMember appUser = new ClubMember();
        appUser.setId(1);
        appUser.setUsername("davidsaltic");
        appUser.setPassword("123");
        appUser.setMemberGroup(memberGroup);

        memberGroupController.insertGroup(memberGroup);
        appUserController.insertUser(appUser);
    }
}