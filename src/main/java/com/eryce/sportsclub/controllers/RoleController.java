package com.eryce.sportsclub.controllers;

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

    @GetMapping("/roles")
    public List<Role> getAll()
    {
        return roleService.getAll();
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> insert(@RequestBody Role role)
    {
        return roleService.insert(role);
    }

}
