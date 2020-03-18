package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.FileRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private RoleRepository roleRepository;

    public String parseCsv(FileRequestDTO fileRequestDTO) {
        String[] lines = fileRequestDTO.getCsvText().split("\n");

        String response="Completed.\n\n";

        for(String line:lines)
        {
            if(isValidLine(line))
                parseLine(line);
            else
                response = response.concat("Skipped line:\n"+line+"\n");
        }

        response=response.concat("\nBecause of invalid input or duplicate username/jmbg values.");
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

        return username!=null && name!=null && surname!=null && isValidJmbg(jmbg) && isValidRole(role) && appUserNotExists(username,jmbg);
    }

    private boolean isValidJmbg(String jmbg)
    {
        return jmbg!=null && jmbg.length()==13;
    }

    private boolean isValidRole(String role)
    {
        String upperCaseRole = role.toUpperCase();
        return upperCaseRole.equals(Roles.COACH) || upperCaseRole.equals(Roles.MANAGER) || upperCaseRole.equals(Roles.MEMBER);
    }

    private boolean appUserNotExists(String username,String jmbg)
    {
        return appUserRepository.findByUsernameIgnoreCase(username)==null && appUserRepository.findByJmbgIgnoreCase(jmbg)==null;
    }

    private void parseLine(String line)
    {
        String [] userProperties = line.split(",");
        String username = userProperties[0];
        String name = userProperties[1];
        String surname = userProperties[2];
        String jmbg = userProperties[3];
        String address= userProperties[4];
        String phoneNumber = userProperties[5];
        String groupName = userProperties[6].trim();
        String roleName = userProperties[7];

        MemberGroup memberGroup=null;

        if(groupName.length()>0)
            memberGroup = findMemberGroupByNameOrCreateNew(groupName);

        Role role = roleRepository.findByNameIgnoreCase(roleName);

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setName(name);
        appUser.setSurname(surname);
        appUser.setJmbg(jmbg);
        appUser.setPassword(jmbg);
        appUser.setAddress(address);
        appUser.setPhoneNumber(phoneNumber);
        appUser.setMemberGroup(memberGroup);
        appUser.setRole(role);

        appUserRepository.save(appUser);
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
