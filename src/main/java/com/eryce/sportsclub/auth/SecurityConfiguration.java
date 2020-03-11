package com.eryce.sportsclub.auth;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(Routes.APP_USERS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERS)
                .antMatchers(Routes.ATTENDANCES_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.MEMBER_GROUPS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERS)
                .antMatchers(Routes.MEMBERSHIPS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.PAYMENTS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.PERMISSIONS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.ROLES_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.TRAINING_SESSIONS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.AUTHENTICATE_BASE+Routes.ANY).permitAll()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){return NoOpPasswordEncoder.getInstance();}
}
