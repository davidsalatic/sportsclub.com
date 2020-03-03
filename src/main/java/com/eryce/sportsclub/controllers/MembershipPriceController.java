package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.MembershipPrice;
import com.eryce.sportsclub.services.MembershipPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MembershipPriceController {

    @Autowired
    private MembershipPriceService membershipPriceService;

    @PostMapping("/memberships/price")
    public ResponseEntity<MembershipPrice> setMembershipPrice(@RequestBody MembershipPrice membershipPrice)
    {
        return membershipPriceService.setMembershipPrice(membershipPrice);
    }

    @GetMapping("/memberships/price")
    public MembershipPrice getMembershipPrice()
    {
        return membershipPriceService.getMembershipPrice();
    }
}