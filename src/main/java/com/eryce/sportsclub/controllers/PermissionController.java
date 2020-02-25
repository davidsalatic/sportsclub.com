package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissions")
    public List<Permission> getAll()
    {
        return permissionService.getAll();
    }

    @PutMapping("/permissions")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission)
    {
        return permissionService.updatePermission(permission);
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> insertPermission(@RequestBody Permission permission)
    {
        return permissionService.insertPermission(permission);
    }

    @DeleteMapping("/permissions")
    public ResponseEntity<Permission> deletePermission(@RequestBody Permission permission)
    {
        return permissionService.deletePermission(permission);
    }

    @GetMapping("/permissions/user/{id}")
    public List<Permission> getPermissionsByUser(@PathVariable Integer id)
    {
        return permissionService.getPermissionsByUser(id);
    }
}
