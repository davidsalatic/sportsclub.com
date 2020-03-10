package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RoleController  {

    @Autowired
    private RoleService roleService;

    @GetMapping(Routes.ROLES_BASE)
    public List<Role> getAll()
    {
        return roleService.getAll();
    }

    @GetMapping(Routes.ROLES_BASE+"/search")
    public Role getByName(@RequestParam String name)
    {
        return roleService.getByName(name);
    }

    @PostMapping(Routes.ROLES_BASE)
    public ResponseEntity<Role> insert(@RequestBody Role role)
    {
        return roleService.insert(role);
    }

}
