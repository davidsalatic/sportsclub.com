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

    @PostMapping("/membership-price")
    public ResponseEntity<MembershipPrice> setMembershipPrice(@RequestBody Integer price)
    {
        return membershipPriceService.setMembershipPrice(price);
    }

    @GetMapping("/membership-price")
    public MembershipPrice getMembershipPrice()
    {
        return membershipPriceService.getMembershipPrice();
    }
}
