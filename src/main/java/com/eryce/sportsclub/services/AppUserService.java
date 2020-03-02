package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.*;
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
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private PaymentRepository paymentRepository;

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
        AppUser appUser = getById(id);
        clearDependencies(appUser);
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void clearDependencies(AppUser appUser) {
        appUser.setMemberGroup(null);
        appUser.setRole(null);
        for(Attendance attendance : attendanceRepository.findAllByAppUser(appUser))
        {
            attendanceRepository.delete(attendance);
        }
        for(Payment payment: paymentRepository.findAllByAppUser(appUser))
        {
            paymentRepository.delete(payment);
        }
    }

    public List<Permission> getUserPermissions(Integer id) {
        AppUser appUser = getById(id);
        return appUser.getRole().getPermissions();
    }

    public ResponseEntity<AppUser> assignGroup(Integer id, MemberGroup memberGroup) {
        AppUser appUser = getById(id);

        System.out.println("before assign: "+appUser.getMemberGroup().getName());
        System.out.println("after assign"+memberGroup.getName() );
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
