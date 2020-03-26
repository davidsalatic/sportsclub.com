package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.controllers.AppUserController;
import com.eryce.sportsclub.controllers.PermissionController;
import com.eryce.sportsclub.controllers.RoleController;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.AppUserService;
import com.eryce.sportsclub.services.PermissionService;
import com.eryce.sportsclub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private PermissionService permissionService;
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
        if(permissionService.getAll().isEmpty())
        {
            Permission accessMemberships = new Permission();
            accessMemberships.setName(Permissions.ACCESS_MEMBERSHIPS);

            Permission accessTrainingSessions = new Permission();
            accessTrainingSessions.setName(Permissions.ACCESS_TRAINING_SESSIONS);

            Permission accessMembers = new Permission();
            accessMembers.setName(Permissions.ACCESS_MEMBERS);

            Permission accessSelf = new Permission();
            accessSelf.setName(Permissions.ACCESS_SELF);

            Role memberRole = new Role();
            memberRole.setName(Roles.MEMBER);
            memberRole.getPermissions().add(accessSelf);

            Role coachRole = new Role();
            coachRole.setName(Roles.COACH);
            coachRole.getPermissions().add(accessTrainingSessions);
            coachRole.getPermissions().add(accessSelf);
            coachRole.getPermissions().add(accessMembers);

            Role managerRole = new Role();
            managerRole.setName(Roles.MANAGER);
            managerRole.getPermissions().add(accessMemberships);
            managerRole.getPermissions().add(accessTrainingSessions);
            managerRole.getPermissions().add(accessSelf);
            managerRole.getPermissions().add(accessMembers);

            permissionService.insert(accessMemberships);
            permissionService.insert(accessTrainingSessions);
            permissionService.insert(accessMembers);
            permissionService.insert(accessSelf);

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