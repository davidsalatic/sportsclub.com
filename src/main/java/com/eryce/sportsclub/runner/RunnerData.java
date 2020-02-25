package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.controllers.MemberGroupController;
import com.eryce.sportsclub.controllers.RoleController;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private RoleController roleController;
    @Autowired
    private MemberGroupController memberGroupController;

    @Override
    public void run(String... args) {


        insertTestData();
        System.out.println("***************APP STARTED*****************");
    }

    private void insertTestData() {
//        insertGroupsTestData();
        insertRoles();
    }

    private void insertGroupsTestData() {
        MemberGroup memberGroup  = new MemberGroup();
        memberGroup.setName("1.Grupa");

        MemberGroup memberGroup1 = new MemberGroup();
        memberGroup1    .setName("2.Grupa");

        memberGroupController.insertGroup(memberGroup);
        memberGroupController.insertGroup(memberGroup1);
    }

    private void insertRoles() {
        Role role = new Role();
    }
}