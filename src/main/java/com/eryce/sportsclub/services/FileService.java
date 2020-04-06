package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.dto.FileRequestDTO;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.eryce.sportsclub.constants.Roles.MANAGER;

@Service
public class FileService {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private RoleRepository roleRepository;

    public String parseCsv(FileRequestDTO fileRequestDTO) {
        String[] linesInFile = fileRequestDTO.getCsvText().split("\n");

        String response="Completed.\n\n";

        for(String line:linesInFile)
        {
            if(isValidLine(line))
                parseLine(line);
            else
                response = response.concat("Skipped line:\n"+line+"\n");
        }

        response=response.concat("\nLines with incorrect input / duplicate values are automatically skipped.");
        return response;
    }

    private boolean isValidLine(String line)
    {
        String [] userProperties = line.split(",");
        String username = userProperties[0];
        String name = userProperties[1];
        String surname = userProperties[2];
        String jmbg = userProperties[3];
        String role = userProperties[7].trim();

        return hasEmailIfStaff(username,role) && usernameIsValid(username) && name!=null && surname!=null && appUserService.isValidJmbg(jmbg) && isValidRole(role) && appUserNotExists(username,jmbg);
    }

    private boolean hasEmailIfStaff(String username, String role) {
        if(role.equals(MANAGER) || role.equals(Roles.COACH))
            return username != null;
        return true;
    }

    private boolean usernameIsValid(String username)
    {
        if(username.length()>0)//if username was entered
        {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            return pat.matcher(username).matches();
        }
        else return true;
    }

    private boolean isValidRole(String role)
    {
        String upperCaseRole = role.toUpperCase();
        return upperCaseRole.equals(Roles.COACH) || upperCaseRole.equals(MANAGER) || upperCaseRole.equals(Roles.MEMBER);
    }

    private boolean appUserNotExists(String username,String jmbg)
    {
        return appUserService.getByUsername(username)==null && appUserService.getByJmbg(jmbg)==null;
    }

    private void parseLine(String line)
    {
        line = line.replace('\r',' ');

        String [] userProperties = line.split(",");
        String username = userProperties[0];
        String name = userProperties[1];
        String surname = userProperties[2];
        String jmbg = userProperties[3];
        String address= userProperties[4];
        String phoneNumber = userProperties[5];
        String groupName = userProperties[6].trim();
        String roleName = userProperties[7].trim();

        MemberGroup memberGroup=null;

        if(groupName.length()>0)
            memberGroup = findMemberGroupByNameOrCreateNew(groupName);

        Role role = roleRepository.findByNameContainingIgnoreCase(roleName);

        AppUserRequestDTO appUser = new AppUserRequestDTO();
        appUser.setUsername(username);
        appUser.setName(name);
        appUser.setSurname(surname);
        appUser.setJmbg(jmbg);
        appUser.setAddress(address);
        appUser.setPhoneNumber(phoneNumber);
        appUser.setMemberGroup(memberGroup);
        appUser.setRole(role);

        appUserService.insert(appUser);
    }

    private MemberGroup findMemberGroupByNameOrCreateNew(String groupName) {
        MemberGroup memberGroup = memberGroupRepository.findByName(groupName);
        if(memberGroup==null)
        {
            memberGroup = new MemberGroup();
            memberGroup.setName(groupName);
            memberGroupRepository.save(memberGroup);
        }
        return memberGroup;
    }

}
