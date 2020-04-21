package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.configuration.ApplicationValues;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.AppUserService;
import com.eryce.sportsclub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private ApplicationValues applicationValues;

    @Override
    public void run(String... args) {
        insertRoles();
        System.out.println("***************APP STARTED*****************");
    }


    private void insertRoles() {
        if(roleService.getAll().isEmpty())
        {
            Role memberRole = new Role();
            memberRole.setName(Roles.MEMBER);

            Role coachRole = new Role();
            coachRole.setName(Roles.COACH);

            Role managerRole = new Role();
            managerRole.setName(Roles.MANAGER);

            roleService.insert(memberRole);
            roleService.insert(coachRole);
            roleService.insert(managerRole);

            if(appUserService.getAllStaff().isEmpty())
            {
                AppUserRequestDTO defaultManager = applicationValues.getDefaultUser();
                defaultManager.setRole(managerRole);
                appUserService.insert(defaultManager);
            }
        }
    }
}