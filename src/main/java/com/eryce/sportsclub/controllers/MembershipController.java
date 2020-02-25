package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Membership> getAllByUserId(@PathVariable("id") Integer id)
    {
        return membershipService.getAllByUserId(id);
    }

    @GetMapping("/memberships/{year}/{month}")
    public List<Membership> getAllByYearAndMonth(@PathVariable("year")Integer year,@PathVariable("month")Integer month)
    {
        return membershipService.getAllByYearAndMonth(year,month);
    }

    @PutMapping("/memberships")
    public ResponseEntity<Membership> updateMembership(@RequestBody Membership membership)
    {
        return membershipService.updateMembership(membership);
    }

    @PostMapping("/memberships")
    public ResponseEntity<Membership> insertMembership(@RequestBody Membership membership)
    {
        return membershipService.insertMemberShipIfExists(membership);
    }

    @DeleteMapping("/memberships")
    public ResponseEntity<Membership> deleteMembership(@RequestBody Membership membership)
    {
        return membershipService.deleteMembership(membership);
    }

}
