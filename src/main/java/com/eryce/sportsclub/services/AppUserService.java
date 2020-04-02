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

import java.time.DateTimeException;
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
    @Autowired
    private CompetitionApplicationRepository competitionApplicationRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public List<AppUser> getAll()
    {
        return appUserRepository.findAll();
    }

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

    public List<String> getEmails(List<AppUser>appUsers)
    {
        List<String> emailAddresses = new ArrayList<>();
        for(AppUser appUser:appUsers)
            emailAddresses.add(appUser.getUsername());
        return emailAddresses;
    }

    public ResponseEntity<AppUser> insert(AppUserRequestDTO appUserRequestDTO) {
        AppUser appUser = appUserRequestDTO.generateAppUser();
        if(appUser.getDateJoined()==null)
            appUser.setDateJoined(LocalDate.now());

        parseJmbg(appUser);
        appUserRepository.save(appUser);

        //if email was entered, send reg mail
        if(appUserRequestDTO.getUsername()!=null && !appUserRequestDTO.getUsername().equals(""))
            sendRegistrationEmail(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void parseJmbg(AppUser appUser) {
        if(isValidJmbg(appUser.getJmbg()))
            extractAndSetValues(appUser);
    }

    public boolean isValidJmbg(String jmbg) {
        return isValidJmbgLength(jmbg) && isNumeric(jmbg) && isValidJmbgFormat(jmbg);
    }

    private boolean isValidJmbgLength(String jmbg) {
        return jmbg!=null && jmbg.length()==13;
    }

    private boolean isNumeric(String jmbg)
    {
        return jmbg.matches("\\d+");
    }

    private boolean isValidJmbgFormat(String jmbg) {
        try
        {
            String formattedDate = formatDateText(getUnformattedDatePartFromJmbg(jmbg));
            LocalDate.parse(formattedDate); //ISO_LOCAL_DATE default formatting
            return true;
        }catch (DateTimeException dte)
        {
            return false;
        }
    }

    private String getUnformattedDatePartFromJmbg(String jmbg)
    {
        return jmbg.substring(0,7);
    }

    private String formatDateText(String unformattedDate)  //example: "31123997"
    {
        String formattedDate="";
        //INPUT:'ddMMyyy',  OUTPUT: 'yyyyMMdd'
        boolean isBornBefore2000 = true;
        if(unformattedDate.charAt(4)=='0') //only for people born after 1999
            isBornBefore2000=false;

        if(isBornBefore2000)
            formattedDate+="1";
        else
            formattedDate+="2";

        formattedDate+=unformattedDate.substring(4,7)+"-"; //1997-
        formattedDate+=unformattedDate.substring(2,4)+"-"; //1997-12-
        formattedDate+=unformattedDate.substring(0,2); //1997-12-31

        return formattedDate;
    }

    private void extractAndSetValues(AppUser appUser) {
        String formattedDateText = formatDateText(getUnformattedDatePartFromJmbg(appUser.getJmbg()));
        appUser.setDateOfBirth(LocalDate.parse(formattedDateText));
        appUser.setGender(getGenderFromJmbg(appUser.getJmbg()));
    }

    private String getGenderFromJmbg(String jmbg) {
        String genderPartOfJmbg = jmbg.substring(9,12);
        int genderCode = Integer.parseInt(genderPartOfJmbg);
        if(genderCode<500)
            return "Male";
        else
            return "Female";
    }


    private void sendRegistrationEmail(AppUser appUser)
    {
        final String token = jwtTokenProvider.createToken(appUser.getUsername(),appUser);
        //async sending email
        mailService.sendRegistrationMessage(appUser.getUsername(),token);
    }

    public ResponseEntity<AppUser> update(AppUserRequestDTO appUserRequestDTO) {

        AppUser appUserBeforeUpdate = appUserRepository.getOne(appUserRequestDTO.getId());

        String usernameBeforeUpdate = appUserBeforeUpdate.getUsername();
        String usernameInRequest = appUserRequestDTO.getUsername();

        String jmbgBeforeUpdate = appUserBeforeUpdate.getJmbg();
        String jmbgAfterUpdate = appUserRequestDTO.getJmbg();

        if(isAddingEmail(usernameBeforeUpdate,usernameInRequest)
                || isEditingEmail(usernameBeforeUpdate,usernameInRequest))
            sendRegistrationEmail(appUserRequestDTO.generateAppUser());

        AppUser updatedAppUser = appUserRequestDTO.generateAppUser();

        if(isEditingJmbg(jmbgBeforeUpdate,jmbgAfterUpdate))
            parseJmbg(updatedAppUser);

        this.appUserRepository.save(updatedAppUser);
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

    private boolean isEditingJmbg(String jmbgBeforeUpdate, String jmbgAfterUpdate) {
        return !jmbgBeforeUpdate.equals(jmbgAfterUpdate);
    }

    public ResponseEntity<AppUser> updateSelf(AppUserRequestDTO appUserRequestDTO) {
        return this.update(appUserRequestDTO);
    }

    public ResponseEntity<AppUser> delete(Integer id) {
        AppUser appUser = getById(id);
        deleteAttendancesForUser(appUser);
        deletePaymentsForUser(appUser);
        deleteApplicationsForUser(appUser);
        deleteCommentsByUser(appUser);
        deletePostsByUser(appUser);
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteCommentsByUser(AppUser appUser) {
        for(Comment comment: commentRepository.findAllByAppUser(appUser))
            commentRepository.delete(comment);
    }

    private void deletePostsByUser(AppUser appUser) {
        for(Post post : postRepository.findAllByAppUser(appUser))
            postRepository.delete(post);
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
        for(CompetitionApplication competitionApplication : competitionApplicationRepository.findAllByAppUser(appUser))
            competitionApplicationRepository.delete(competitionApplication);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getByUsername(s);
    }
}