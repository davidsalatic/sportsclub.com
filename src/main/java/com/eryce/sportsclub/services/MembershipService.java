package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    public List<Membership> getAll() {

        return membershipRepository.findAll();
    }

    public List<Membership> getAllByUserId(Integer userId) {
        return null;
    }
}
