package com.eryce.sportsclub.services;

import com.eryce.sportsclub.security.jwt.JWT;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

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

    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsernameIgnoreCase(username);
    }

    public AppUser getByJmbg(String jmbg) {
        return appUserRepository.findByJmbgIgnoreCase(jmbg);
    }

    public ResponseEntity<AppUser> insert(AppUserRequestDTO appUserRequestDTO) {
        appUserRepository.save(appUserRequestDTO.generateAppUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AppUser> update(AppUserRequestDTO appUserRequestDTO) {
        return this.insert(appUserRequestDTO);
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getByUsername(s);
    }

    public String getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            AppUser appUser = ((AppUser)principal);
            return JWT.generateToken(appUser);
        } else {
            return null;
        }
    }
}