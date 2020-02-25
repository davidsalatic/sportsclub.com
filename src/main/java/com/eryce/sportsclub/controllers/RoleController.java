package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController  {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public List<Role> getAll()
    {
        return roleService.getAll();
    }

    @PostMapping("/roles")
    public ResponseEntity insertRole(@RequestBody Role role)
    {
        return roleService.insertRole(role);
    }

    @PutMapping("/roles")
    public ResponseEntity updateRole(@RequestBody Role role)
    {
        return roleService.updateRole(role);
    }

    @DeleteMapping("/roles")
    public ResponseEntity deleteRole(@RequestBody Role role)
    {
        return roleService.deleteRole(role);
    }

}
