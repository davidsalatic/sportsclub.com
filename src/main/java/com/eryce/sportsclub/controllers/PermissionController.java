package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping(Routes.PERMISSIONS_BASE)
    public List<Permission> getAll()
    {
        return permissionService.getAll();
    }

    @PostMapping(Routes.PERMISSIONS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<Permission> insert(@RequestBody Permission permission)
    {
        return permissionService.insert(permission);
    }
}
