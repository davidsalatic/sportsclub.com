package com.eryce.sportsclub.auth;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.constants.Routes;
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
        http.authorizeRequests().antMatchers(Routes.APP_USERS_BASE).hasAnyRole(Roles.MANAGER,Roles.COACH)
                .antMatchers(Routes.ATTENDANCES_BASE).hasAnyRole(Roles.MANAGER,Roles.COACH)
                .antMatchers(Routes.MEMBER_GROUPS_BASE).hasAnyRole(Roles.MANAGER,Roles.COACH)
                .antMatchers(Routes.MEMBERSHIPS_BASE).hasRole(Roles.MANAGER)
                .antMatchers(Routes.PAYMENTS_BASE).hasRole(Roles.MANAGER)
                .antMatchers(Routes.PERMISSIONS_BASE).hasRole(Roles.MANAGER)
                .antMatchers(Routes.ROLES_BASE).hasRole(Roles.MANAGER)
                .antMatchers(Routes.TRAINING_SESSIONS_BASE).hasAnyRole(Roles.MANAGER,Roles.COACH)
                .antMatchers(Routes.AUTHENTICATE_BASE).permitAll()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){return NoOpPasswordEncoder.getInstance();}
}
