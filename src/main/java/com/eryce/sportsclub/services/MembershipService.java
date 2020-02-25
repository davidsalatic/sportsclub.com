package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        AppUser appUser = appUserRepository.getOne(userId);
        return membershipRepository.findAllByAppUser(appUser);
    }

    public List<Membership> getAllByYearAndMonth(Integer year, Integer month) {
        return membershipRepository.findAllByYearAndMonth(year,month);
    }

    public ResponseEntity<Membership> updateMembership(Membership membership) {
        membershipRepository.save(membership);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Membership> deleteMembership(Membership membership) {
        membershipRepository.delete(membership);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Membership> insertMemberShipIfExists(Membership membership) {
        membershipRepository.save(membership);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
