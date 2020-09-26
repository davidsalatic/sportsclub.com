package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.configuration.ApplicationValues;
import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.dto.RoleDto;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.AppUserService;
import com.eryce.sportsclub.services.PeriodService;
import com.eryce.sportsclub.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.eryce.sportsclub.constants.Roles.*;

@Component
@AllArgsConstructor
public class RunnerData implements CommandLineRunner {

    private RoleService roleService;
    private AppUserService appUserService;
    private ApplicationValues applicationValues;
    private PeriodService periodService;

    @Override
    public void run(String... args) {
        insertRoles();
        insertDefaultManager();
        insertPeriod();
        System.out.println("*** SPORTS CLUB STARTED ***");
    }

    private void insertRoles() {
        if (roleService.getAll().isEmpty()) {
            Role memberRole = new Role();
            memberRole.setName(MEMBER);

            Role coachRole = new Role();
            coachRole.setName(COACH);

            Role managerRole = new Role();
            managerRole.setName(MANAGER);

            roleService.insert(memberRole.convertToDto());
            roleService.insert(coachRole.convertToDto());
            roleService.insert(managerRole.convertToDto());
        }
    }

    private void insertDefaultManager() {
        RoleDto managerRoleDto = roleService.getByName(MANAGER);

        if (appUserService.getAllStaff().isEmpty()) {
            AppUserDto defaultManager = applicationValues.getDefaultUser();
            defaultManager.setRole(managerRoleDto);
            appUserService.insert(defaultManager);
        }
    }

    private void insertPeriod() {
        periodService.createPeriodIfNotExist();
    }
}