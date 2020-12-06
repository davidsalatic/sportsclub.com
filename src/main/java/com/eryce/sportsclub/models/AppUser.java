package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.AppUserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String username;

    private String password;
    private String name;
    private String surname;

    @Column(unique = true)
    private String jmbg;
    private String address;
    private String phoneNumber;
    private LocalDate dateJoined;
    private LocalDate dateOfBirth;
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    public void setAuthority(Role role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AppUserDto convertToDto() {
        AppUserDto appUserDto = AppUserDto.builder()
                .address(address)
                .dateJoined(dateJoined)
                .dateOfBirth(dateOfBirth)
                .id(id)
                .jmbg(jmbg)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(role.convertToDto())
                .surname(surname)
                .username(username)
                .gender(gender)
                .password(password)
                .build();
        if (memberGroup != null) {
            appUserDto.setMemberGroup(memberGroup.convertToDto());
        }
        return appUserDto;
    }
}