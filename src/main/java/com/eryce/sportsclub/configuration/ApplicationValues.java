package com.eryce.sportsclub.configuration;

import com.eryce.sportsclub.dto.AppUserRequestDTO;
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

    public AppUserRequestDTO getDefaultUser()
    {
        AppUserRequestDTO appUser = new AppUserRequestDTO();
        appUser.setUsername(this.username);
        appUser.setName(this.name);
        appUser.setSurname(this.surname);
        appUser.setJmbg(this.jmbg);
        return appUser;
    }

    public String getMembershipDeadline() {
        return membershipDeadline;
    }
}
