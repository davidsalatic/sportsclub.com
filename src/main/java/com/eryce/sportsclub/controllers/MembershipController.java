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

    @PostMapping("/memberships")
    public ResponseEntity<Membership> insert(@RequestBody Membership membership)
    {
        return membershipService.insert(membership);
    }

    @PutMapping("/memberships")
    public ResponseEntity<Membership> update(@RequestBody Membership membership)
    {
        return membershipService.update(membership);
    }
}