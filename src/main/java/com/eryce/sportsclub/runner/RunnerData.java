package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.controllers.PermissionController;
import com.eryce.sportsclub.controllers.RoleController;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private PermissionController permissionController;
    @Autowired
    private RoleController roleController;

    @Override
    public void run(String... args) {

        insertRolesAndPermissions();

        System.out.println("***************APP STARTED*****************");
    }

    private void insertRolesAndPermissions() {
        if(permissionController.getAll().isEmpty())
        {
            Permission accessMemberships = new Permission();
            accessMemberships.setName(Permissions.ACCESS_MEMBERSHIPS);

            Permission accessTrainingSessions = new Permission();
            accessTrainingSessions.setName(Permissions.ACCESS_TRAINING_SESSIONS);

            Permission accessSelf = new Permission();
            accessSelf.setName(Permissions.ACCESS_SELF);

            Role memberRole = new Role();
            memberRole.setName(Roles.MEMBER);
            memberRole.getPermissions().add(accessSelf);

            Role coachRole = new Role();
            coachRole.setName(Roles.COACH);
            coachRole.getPermissions().add(accessTrainingSessions);
            coachRole.getPermissions().add(accessSelf);

            Role managerRole = new Role();
            managerRole.setName(Roles.MANAGER);
            managerRole.getPermissions().add(accessMemberships);
            managerRole.getPermissions().add(accessTrainingSessions);
            managerRole.getPermissions().add(accessSelf);

            permissionController.insert(accessMemberships);
            permissionController.insert(accessTrainingSessions);
            permissionController.insert(accessSelf);

            roleController.insert(memberRole);
            roleController.insert(coachRole);
            roleController.insert(managerRole);
        }
    }
}