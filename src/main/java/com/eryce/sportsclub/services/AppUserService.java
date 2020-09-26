package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Roles.*;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private AppUserRepository appUserRepository;
    private MemberGroupRepository memberGroupRepository;
    private AttendanceRepository attendanceRepository;
    private PaymentRepository paymentRepository;
    private RoleRepository roleRepository;
    private MailService mailService;
    private CompetitionApplicationRepository competitionApplicationRepository;

    public List<AppUserDto> getAllMembers() {
        Role memberRole = roleRepository.findByNameIgnoreCase(MEMBER);
        List<AppUser> allMembers = appUserRepository.findAllByRole(memberRole);
        return convertToDto(allMembers);
    }

    public List<AppUserDto> getUngroupedMembers() {
        Role memberRole = roleRepository.findByNameIgnoreCase(MEMBER);
        List<AppUser> ungroupedMembers = appUserRepository.findAllByMemberGroupAndRole(null, memberRole);
        return convertToDto(ungroupedMembers);
    }

    public List<AppUserDto> getAllStaff() {
        Role coachRole = roleRepository.findByNameIgnoreCase(COACH);
        Role managerRole = roleRepository.findByNameIgnoreCase(MANAGER);
        List<AppUser> coaches = appUserRepository.findAllByRole(coachRole);
        List<AppUser> managers = appUserRepository.findAllByRole(managerRole);

        List<AppUser> staff = new ArrayList<>(coaches);
        staff.addAll(managers);
        return convertToDto(staff);
    }

    public List<AppUserDto> getAllInMemberGroup(Integer memberGroupId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(memberGroupId);
        List<AppUser> appUsers = appUserRepository.findAllByMemberGroup(memberGroup);
        return convertToDto(appUsers);
    }

    public AppUserDto getById(Integer id) {
        return appUserRepository.getOne(id).convertToDto();
    }

    public AppUserDto getByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username);
        if (appUser != null) {
            return appUser.convertToDto();
        }
        return null;
    }

    public AppUserDto getByJmbg(String jmbg) {
        AppUser appUser = appUserRepository.findByJmbgIgnoreCase(jmbg);
        if (appUser != null) {
            appUser.convertToDto();
        }
        return null;
    }

    public AppUserDto insert(AppUserDto appUserDto) {
        if (userExists(appUserDto.getUsername(), appUserDto.getJmbg())) {
            throw new EntityExistsException();
        }

        AppUser appUser = appUserDto.convertToEntity();
        parseUserJmbg(appUser);

        if (appUser.getDateJoined() == null) {
            appUser.setDateJoined(LocalDate.now());
        }
        if (!isEmpty(appUser.getUsername())) {
            mailService.sendRegistrationMessage(appUser);
        }
        return appUserRepository.save(appUser).convertToDto();
    }

    public boolean userExists(String username, String jmbg) {
        return userNameExists(username) || jmbgExists(jmbg);
    }

    private boolean userNameExists(String username) {
        return appUserRepository.findByUsernameIgnoreCase(username) != null;
    }

    private boolean jmbgExists(String jmbg) {
        return appUserRepository.findByJmbgIgnoreCase(jmbg) != null;
    }

    private void parseUserJmbg(AppUser appUser) {
        String userJmbg = appUser.getJmbg();
        if (isValidJmbg(userJmbg)) {
            String formattedDateText = formatDateText(getUnformattedDatePartFromJmbg(userJmbg));
            appUser.setDateOfBirth(LocalDate.parse(formattedDateText));
            appUser.setGender(getGenderFromJmbg(userJmbg));
        }
    }

    public boolean isValidJmbg(String jmbg) {
        boolean validLength = jmbg != null && jmbg.length() == 13;
        if (!validLength) {
            return false;
        }
        boolean numeric = jmbg.matches("\\d+");
        return numeric && isValidJmbgFormat(jmbg);
    }

    private boolean isValidJmbgFormat(String jmbg) {
        try {
            String formattedDate = formatDateText(getUnformattedDatePartFromJmbg(jmbg));
            LocalDate.parse(formattedDate); //ISO_LOCAL_DATE default formatting
            return true;
        } catch (DateTimeException dte) {
            return false;
        }
    }

    private String getUnformattedDatePartFromJmbg(String jmbg) {
        return jmbg.substring(0, 7);
    }

    private String formatDateText(String unformattedDate) //yyyyMMdd
    {
        String formattedDate = "";

        boolean isBornAfter1999 = unformattedDate.charAt(4) == '0';
        if (isBornAfter1999) {
            formattedDate += "2";
        } else {
            formattedDate += "1";
        }

        formattedDate += unformattedDate.substring(4, 7) + "-"; //yyyy-
        formattedDate += unformattedDate.substring(2, 4) + "-"; //yyyy-MM-
        formattedDate += unformattedDate.substring(0, 2); //yyyy-MM-dd

        return formattedDate;
    }

    private String getGenderFromJmbg(String jmbg) {
        String genderPartOfJmbg = jmbg.substring(9, 12);
        int genderCode = Integer.parseInt(genderPartOfJmbg);
        if (genderCode < 500) {
            return "Male";
        }
        return "Female";
    }

    public List<String> getEmailsOfUsers(List<AppUserDto> users) {
        List<String> emailAddresses = new ArrayList<>();
        for (AppUserDto user : users) {
            if (!isEmpty(user.getUsername())) {
                emailAddresses.add(user.getUsername());
            }
        }
        return emailAddresses;
    }

    public AppUserDto update(AppUserDto appUserDto) {
        AppUser existingAppUser = appUserRepository.getOne(appUserDto.getId());
        AppUser updatedAppUser = appUserDto.convertToEntity();

        String existingUsername = existingAppUser.getUsername();
        String updatedUsername = updatedAppUser.getUsername();

        String existingJmbg = existingAppUser.getJmbg();
        String updatedJmbg = updatedAppUser.getJmbg();

        if (isAddingEmail(existingUsername, updatedUsername)
                || isEditingEmail(existingUsername, updatedUsername)) {
            updatedAppUser.setPassword(null);
            appUserRepository.save(updatedAppUser);
            mailService.sendRegistrationMessage(updatedAppUser);
        }
        if (isEditingJmbg(existingJmbg, updatedJmbg)) {
            parseUserJmbg(updatedAppUser);
        }

        return appUserRepository.save(updatedAppUser).convertToDto();
    }

    private boolean isAddingEmail(String usernameBeforeUpdate, String usernameInRequest) {
        return usernameBeforeUpdate == null && usernameInRequest != null;
    }

    private boolean isEditingEmail(String usernameBeforeUpdate, String usernameInRequest) {
        return usernameBeforeUpdate != null && (!usernameBeforeUpdate.equals(usernameInRequest));
    }

    private boolean isEditingJmbg(String jmbgBeforeUpdate, String jmbgAfterUpdate) {
        return !jmbgBeforeUpdate.equals(jmbgAfterUpdate);
    }

    public AppUserDto updateSelf(AppUserDto appUserDto) {
        return this.update(appUserDto);
    }

    public void delete(Integer id) {
        AppUser appUser = appUserRepository.getOne(id);
        deleteAttendancesForUser(appUser);
        deletePaymentsForUser(appUser);
        deleteCompetitionApplicationsForUser(appUser);
        appUserRepository.deleteById(id);
    }

    private void deleteAttendancesForUser(AppUser appUser) {
        attendanceRepository.deleteInBatch(attendanceRepository.findAllByAppUser(appUser));
    }

    private void deletePaymentsForUser(AppUser appUser) {
        paymentRepository.deleteInBatch(paymentRepository.findAllByAppUser(appUser));
    }

    private void deleteCompetitionApplicationsForUser(AppUser appUser) {
        competitionApplicationRepository.deleteInBatch(competitionApplicationRepository.findAllByAppUser(appUser));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsernameIgnoreCase(username);
    }

    private List<AppUserDto> convertToDto(List<AppUser> appUsers) {
        List<AppUserDto> usersDto = new ArrayList<>();
        for (AppUser appUser : appUsers) {
            usersDto.add(appUser.convertToDto());
        }
        return usersDto;
    }

}