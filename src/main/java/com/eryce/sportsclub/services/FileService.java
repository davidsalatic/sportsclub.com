package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.dto.FileDto;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.eryce.sportsclub.constants.Roles.MANAGER;
import static org.springframework.util.StringUtils.*;

@Service
@AllArgsConstructor
public class FileService {

    private AppUserService appUserService;
    private MemberGroupRepository memberGroupRepository;
    private RoleRepository roleRepository;

    public String parseCsv(FileDto fileDto) {
        String[] linesInFile = fileDto.getCsvText().split("\n");

        String response = "Completed.\n\n";

        for (String line : linesInFile) {
            if (isValidLine(line)) {
                parseLine(line);
            } else {
                response = response.concat("Skipped line:\n" + line + "\n");
            }
        }

        return response.concat("\nLines with incorrect input / duplicate values are automatically skipped.");
    }

    private boolean isValidLine(String line) {
        String[] userProperties = line.split(",");
        if (userProperties.length != 8) {
            return false;
        }

        String username = userProperties[0];
        String name = userProperties[1];
        String surname = userProperties[2];
        String jmbg = userProperties[3];
        String role = userProperties[7].trim();

        return hasEmailIfStaff(username, role) && usernameIsValid(username) && name != null && surname != null && appUserService.isValidJmbg(jmbg) && isValidRole(role) && userNotExists(username, jmbg);
    }

    private boolean hasEmailIfStaff(String username, String role) {
        if (role.equals(MANAGER) || role.equals(Roles.COACH)) {
            return username != null;
        }
        return true;
    }

    private boolean usernameIsValid(String username) {
        if (!isEmpty(username)) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(username).matches();
        }
        return true;
    }

    private boolean isValidRole(String role) {
        String upperCaseRole = role.toUpperCase();
        return upperCaseRole.equals(Roles.COACH) || upperCaseRole.equals(MANAGER) || upperCaseRole.equals(Roles.MEMBER);
    }

    private boolean userNotExists(String username, String jmbg) {
        return appUserService.getByUsername(username) == null && appUserService.getByJmbg(jmbg) == null;
    }

    private void parseLine(String line) {
        line = line.replace('\r', ' ');

        String[] userProperties = line.split(",");
        String username = userProperties[0];
        String name = userProperties[1];
        String surname = userProperties[2];
        String jmbg = userProperties[3];
        String address = userProperties[4];
        String phoneNumber = userProperties[5];
        String groupName = userProperties[6].trim();
        String roleName = userProperties[7].trim();

        MemberGroup memberGroup = null;

        if (!isEmpty(groupName)) {
            memberGroup = findMemberGroupByNameOrCreateNew(groupName);
        }

        Role role = roleRepository.findByNameContainingIgnoreCase(roleName);

        AppUserDto user = AppUserDto.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .jmbg(jmbg)
                .address(address)
                .phoneNumber(phoneNumber)
                .role(role.convertToDto())
                .build();
        if (memberGroup != null) {
            user.setMemberGroup(memberGroup.convertToDto());
        }
        appUserService.insert(user);
    }

    private MemberGroup findMemberGroupByNameOrCreateNew(String groupName) {
        MemberGroup memberGroup = memberGroupRepository.findByName(groupName);
        if (memberGroup == null) {
            memberGroup = new MemberGroup();
            memberGroup.setName(groupName);
            memberGroupRepository.save(memberGroup);
        }
        return memberGroup;
    }
}
