package com.eryce.sportsclub.security;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
        http.csrf().disable().authorizeRequests()
                .antMatchers(Routes.ATTENDANCES_BASE+"/member"+Routes.ANY).hasAuthority(Permissions.ACCESS_SELF)
                .antMatchers(Routes.ATTENDANCES_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.MEMBER_GROUPS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERS)
                .antMatchers(Routes.MEMBERSHIPS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.PAYMENTS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.PERMISSIONS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.ROLES_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.TRAINING_SESSIONS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.APP_USERS_BASE+"/staff").hasAuthority(Permissions.ACCESS_MEMBERSHIPS)
                .antMatchers(Routes.APP_USERS_BASE+"/search/username").hasAuthority(Permissions.ACCESS_SELF)
                .antMatchers(Routes.APP_USERS_BASE+"/update-self").hasAuthority(Permissions.ACCESS_SELF)
                .antMatchers(Routes.APP_USERS_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_MEMBERS)
                .antMatchers(Routes.TERM_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers(Routes.PERIOD_BASE+Routes.ANY).hasAuthority(Permissions.ACCESS_TRAINING_SESSIONS)
                .antMatchers("/logged/details").permitAll()
                .and().formLogin().defaultSuccessUrl("http://localhost:4200/home")
                .and().logout()
        ;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){return NoOpPasswordEncoder.getInstance();}
}