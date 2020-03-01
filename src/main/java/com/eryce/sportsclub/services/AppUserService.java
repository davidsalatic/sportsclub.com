package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.PermissionRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public Collection<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getById(Integer id) {
        return appUserRepository.getOne(id);
    }

    public ResponseEntity<AppUser> insertUserIfNotExists(AppUser appUser) {
        if(exists(appUser))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean exists(AppUser appUser) {
        return existsByUsername(appUser.getUsername());
    }

    private boolean existsByUsername(String username) {
        return !(appUserRepository.findAllByUsernameContainingIgnoreCase(username).isEmpty());
    }

    public ResponseEntity<AppUser> updateUserIfExists(AppUser appUser) {
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AppUser> deleteUserIfExists(Integer id) {
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Permission> getUserPermissions(Integer id) {
        AppUser appUser = getById(id);
        return appUser.getRole().getPermissions();
    }

    public ResponseEntity<AppUser> assignGroup(Integer id, MemberGroup memberGroup) {
        AppUser appUser = getById(id);
        appUser.setMemberGroup(memberGroup);
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<AppUser> findAllByNameOrSurnameContainingIgnoreCase(String name, String surname) {
        return appUserRepository.findAllByNameOrSurnameContainingIgnoreCase(name,surname);
    }

    public List<AppUser> getUsersInGroup(Integer id) {
        MemberGroup memberGroup = memberGroupRepository.getOne(id);
        return appUserRepository.findAllByMemberGroup(memberGroup);
    }

    public ResponseEntity<AppUser> removeMemberGroup(Integer id) {
        AppUser appUser = getById(id);
        appUser.setMemberGroup(null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
