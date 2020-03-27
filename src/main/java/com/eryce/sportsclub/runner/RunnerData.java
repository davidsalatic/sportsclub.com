package com.eryce.sportsclub.runner;

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

    @Override
    public void run(String... args) {
        insertRolesAndPermissions();
        System.out.println("***************APP STARTED*****************");
    }


    private void insertRolesAndPermissions() {
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
                AppUserRequestDTO defaultManager = new AppUserRequestDTO();
                defaultManager.setId(99999);
                defaultManager.setName("Manager");
                defaultManager.setSurname("Manager");
                defaultManager.setJmbg("0000000000000");
                defaultManager.setUsername("manager@sports.com");
                defaultManager.setPassword("manager");
                defaultManager.setRole(managerRole);
                appUserService.insert(defaultManager);
            }
        }
    }
}