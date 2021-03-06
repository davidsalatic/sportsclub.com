package com.eryce.sportsclub.configuration;

import com.eryce.sportsclub.dto.AppUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationValues {

    @Value("${values.membershipDeadline}")
    private String membershipDeadline;
    @Value("${default_user.default_username}")
    private String username;
    @Value("${default_user.default_name}")
    private String name;
    @Value("${default_user.default_surname}")
    private String surname;
    @Value("${default_user.default_jmbg}")
    private String jmbg;

    public AppUserDto getDefaultUser() {
        return AppUserDto.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .jmbg(jmbg)
                .build();
    }

    public String getMembershipDeadline() {
        return membershipDeadline;
    }
}
