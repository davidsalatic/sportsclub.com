package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public ResponseEntity insertRole(Role role) {
        roleRepository.save(role);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateRole(Role role) {
        roleRepository.save(role);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteRole(Role role)
    {
        roleRepository.delete(role);
        return new ResponseEntity(HttpStatus.OK);
    }
}
