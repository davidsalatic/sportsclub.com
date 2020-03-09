package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
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
    @Autowired
    private RoleRepository roleRepository;

    public List<AppUser> getAllMembers() {
        Role memberRole = roleRepository.findByNameIgnoreCase(Roles.MEMBER);
        return appUserRepository.findAllByRole(memberRole);
    }

    public List<AppUser> getUngroupedMembers() {
        List<AppUser>ungrouped = new ArrayList<>();
        List<AppUser> allMembers = this.getAllMembers();
        for(AppUser appUser : allMembers)
        {
            if(appUser.getMemberGroup()==null)
                ungrouped.add(appUser);
        }
        return ungrouped;
    }

    public List<AppUser> getAllInMemberGroup(Integer id) {
        MemberGroup memberGroup = memberGroupRepository.getOne(id);
        return appUserRepository.findAllByMemberGroup(memberGroup);
    }

    public AppUser getById(Integer id) {
        return appUserRepository.getOne(id);
    }

    public List<AppUser> getByUsername(String username) {
        return appUserRepository.findAllByUsernameIgnoreCase(username);
    }

    public List<AppUser> getByJmbg(String jmbg) {
        return appUserRepository.findAllByJmbgIgnoreCase(jmbg);
    }

    public ResponseEntity<AppUser> insert(AppUser appUser) {
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AppUser> update(AppUser appUser) {
        return this.insert(appUser);
    }

    public ResponseEntity<AppUser> delete(Integer id) {
        AppUser appUser = getById(id);
        deleteAttendancesForUser(appUser);
        deletePaymentsForUser(appUser);
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteAttendancesForUser(AppUser appUser)
    {
        for(Attendance attendance : attendanceRepository.findAllByAppUser(appUser))
        {
            attendanceRepository.delete(attendance);
        }
    }

    private void deletePaymentsForUser(AppUser appUser) {
        for(Payment payment : paymentRepository.findAllByAppUser(appUser))
        {
            paymentRepository.delete(payment);
        }
    }
}