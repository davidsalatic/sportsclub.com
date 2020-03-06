package com.eryce.sportsclub.services;

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

    public Membership getById(Integer id) {
        return membershipRepository.getOne(id);
    }

    public Membership getByMonthAndYear(Integer month, Integer year) {
        return membershipRepository.findByMonthAndYear(month,year);
    }

    public ResponseEntity<Membership> insert(Membership membership) {
        membershipRepository.save(membership);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Membership> update(Membership membership) {
        return this.insert(membership);
    }
}
