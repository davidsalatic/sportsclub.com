package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.*;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CompetitionRepository competitionRepository;

    public List<AppUser> getAllMembers() {
        Role memberRole = roleRepository.findByNameIgnoreCase(Roles.MEMBER);
        return appUserRepository.findAllByRole(memberRole);
    }

    public List<AppUser> getAllStaff() {
        Role coachRole = roleRepository.findByNameIgnoreCase(Roles.COACH);
        Role managerRole = roleRepository.findByNameIgnoreCase(Roles.MANAGER);
        List<AppUser>coaches = appUserRepository.findAllByRole(coachRole);
        List<AppUser>managers = appUserRepository.findAllByRole(managerRole);

        List<AppUser>staff = new ArrayList<>(coaches);
        staff.addAll(managers);
        return staff;
    }

    public List<AppUser> getUngroupedMembers() {
        List<AppUser>ungrouped = new ArrayList<>();
        List<AppUser> allMembers = this.getAllMembers();
        for(AppUser appUser : allMembers)
            if(appUser.getMemberGroup()==null)
                ungrouped.add(appUser);
        return ungrouped;
    }

    public List<AppUser> getAllInMemberGroup(Integer memberGroupId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(memberGroupId);
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
        AppUser appUser = appUserRequestDTO.generateAppUser();
        if(appUser.getDateJoined()==null)
            appUser.setDateJoined(LocalDate.now());
        appUserRepository.save(appUser);

        if(appUserRequestDTO.getUsername()!="" && appUserRequestDTO.getUsername()!=null)//if email was entered, send reg mail
            sendRegistationEmail(appUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendRegistationEmail(AppUser appUser)
    {
        final String token = jwtTokenProvider.createToken(appUser.getUsername(),appUser);
        //async sending email
        mailService.sendRegistrationMessage(appUser.getUsername(),token);
    }

    public ResponseEntity<AppUser> update(AppUserRequestDTO appUserRequestDTO) {
        String usernameBeforeUpdate = appUserRepository.getOne(appUserRequestDTO.getId()).getUsername();
        String usernameInRequest = appUserRequestDTO.getUsername();

        if(isAddingEmail(usernameBeforeUpdate,usernameInRequest)
                || isEditingEmail(usernameBeforeUpdate,usernameInRequest))
            sendRegistationEmail(appUserRequestDTO.generateAppUser());

        this.appUserRepository.save(appUserRequestDTO.generateAppUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isAddingEmail(String usernameBeforeUpdate, String usernameInRequest)
    {
        return usernameBeforeUpdate==null && usernameInRequest!=null;
    }

    private boolean isEditingEmail(String usernameBeforeUpdate,String usernameInRequest)
    {
        return usernameBeforeUpdate!=null && (!usernameBeforeUpdate.equals(usernameInRequest));
    }

    public ResponseEntity<AppUser> updateSelf(AppUserRequestDTO appUserRequestDTO) {
        return this.update(appUserRequestDTO);
    }

    public ResponseEntity<AppUser> delete(Integer id) {
        AppUser appUser = getById(id);
        deleteAttendancesForUser(appUser);
        deletePaymentsForUser(appUser);
        deleteApplicationsForUser(appUser);
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteAttendancesForUser(AppUser appUser)
    {
        for(Attendance attendance : attendanceRepository.findAllByAppUser(appUser))
            attendanceRepository.delete(attendance);
    }

    private void deletePaymentsForUser(AppUser appUser) {
        for(Payment payment : paymentRepository.findAllByAppUser(appUser))
            paymentRepository.delete(payment);
    }

    private void deleteApplicationsForUser(AppUser appUser) {
        //TODO
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getByUsername(s);
    }
}