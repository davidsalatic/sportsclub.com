package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
