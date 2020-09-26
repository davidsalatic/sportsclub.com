package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.AppUser;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {

    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String jmbg;
    private String address;
    private String phoneNumber;
    private LocalDate dateJoined;
    private LocalDate dateOfBirth;
    private MemberGroupDto memberGroup;
    private RoleDto role;
    private String gender;
    private String password;

    public AppUser convertToEntity() {
        AppUser appUser = AppUser.builder()
                .address(address)
                .dateJoined(dateJoined)
                .dateOfBirth(dateOfBirth)
                .jmbg(jmbg)
                .name(name)
                .phoneNumber(surname)
                .password(password)
                .role(role.convertToEntity())
                .surname(surname)
                .username(username)
                .gender(gender)
                .id(id)
                .build();
        if (memberGroup != null) {
            appUser.setMemberGroup(memberGroup.convertToEntity());
        }
        return appUser;
    }
}
