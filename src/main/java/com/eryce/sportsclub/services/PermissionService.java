package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    public ResponseEntity<Permission> insert(Permission permission) {
        permissionRepository.save(permission);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
