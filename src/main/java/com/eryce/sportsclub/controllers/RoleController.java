package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.ROLES_BASE)
@PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
public class RoleController  {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAll()
    {
        return roleService.getAll();
    }

    @GetMapping("/search")
    public Role getByName(@RequestParam String name)
    {
        return roleService.getByName(name);
    }

    @PostMapping
    public ResponseEntity<Role> insert(@RequestBody Role role)
    {
        return roleService.insert(role);
    }

}
