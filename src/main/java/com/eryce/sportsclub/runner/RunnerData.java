package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.controllers.AppUserController;
import com.eryce.sportsclub.controllers.MemberGroupController;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.ClubMember;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private MemberGroupController memberGroupController;
    @Autowired
    private AppUserController appUserController;

    @Override
    public void run(String... args) {

        insertTestData();
    }

    private void insertTestData() {

        MemberGroup memberGroup = new MemberGroup(1,"grupa1");
        MemberGroup memberGroup1 = new MemberGroup(2,"grupa2");

        memberGroupController.insertGroup(memberGroup);
        memberGroupController.insertGroup(memberGroup1);

        ClubMember clubMember = new ClubMember("davidsalatic","sifra123","David","Salatic");
        ClubMember clubMember1 = new ClubMember("dzon","dzon11","Dzon","Dzonic");

        clubMember.setUserId(1);
        clubMember1.setUserId(2);
        clubMember.setMemberGroup(memberGroup);
        clubMember1.setMemberGroup(memberGroup1);

        appUserController.insertUser(clubMember);
        appUserController.insertUser(clubMember1);

        System.out.println("Started in Runner");
    }
}
