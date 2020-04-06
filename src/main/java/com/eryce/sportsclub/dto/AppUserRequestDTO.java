package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;

import java.time.LocalDate;

public class AppUserRequestDTO {

    private Integer id;

    private String username;
    private String password;
    private String name;
    private String surname;
    private String jmbg;
    private String address;
    private String phoneNumber;
    private LocalDate dateJoined;
    private LocalDate dateOfBirth;
    private String gender;
    private MemberGroup memberGroup;
    private Role role;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(MemberGroup memberGroup) {
        this.memberGroup = memberGroup;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AppUser generateAppUser()
    {
        AppUser appUser = new AppUser();
        if(this.id!=null)
            appUser.setId(id);
        appUser.setName(this.getName());
        appUser.setSurname(this.getSurname());
        appUser.setUsername(this.getUsername());
        appUser.setPassword(this.getPassword());
        appUser.setDateJoined(this.getDateJoined());
        appUser.setRole(this.getRole());
        appUser.setMemberGroup(this.getMemberGroup());
        appUser.setPhoneNumber(this.getPhoneNumber());
        appUser.setAddress(this.getAddress());
        appUser.setJmbg(this.getJmbg());
        appUser.setGender(this.gender);
        appUser.setDateOfBirth(this.dateOfBirth);
        return appUser;
    }
}
