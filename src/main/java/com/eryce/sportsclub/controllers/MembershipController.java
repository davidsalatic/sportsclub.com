package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping("/memberships")
    public List<Membership> getAll()
    {
        return membershipService.getAll();
    }

    @GetMapping("/memberships/{id}")
    public Membership getById(@PathVariable ("id")Integer id)
    {
        return membershipService.getById(id);
    }

    @GetMapping("/memberships/{month}/{year}")
    public Membership getByMonthAndYear(@PathVariable("month")Integer month,@PathVariable("year")Integer year)
    {
        return membershipService.getByMonthAndYear(month,year);
    }

    @PutMapping("/memberships")
    public ResponseEntity<Membership> updateMembership(@RequestBody Membership membership)
    {
        return membershipService.updateMembership(membership);
    }

    @PostMapping("/memberships")
    public ResponseEntity<Membership> insertMembership(@RequestBody Membership membership)
    {
        return membershipService.insertMembership(membership);
    }

    @DeleteMapping("/memberships")
    public ResponseEntity<Membership> deleteMembership(@RequestBody Membership membership)
    {
        return membershipService.deleteMembership(membership);
    }
}