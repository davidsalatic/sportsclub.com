package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.MembershipPrice;
import com.eryce.sportsclub.services.MembershipPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MembershipPriceController {

    @Autowired
    private MembershipPriceService membershipPriceService;

    @GetMapping(Routes.MEMBERSHIPS_BASE+"/price")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERSHIPS+"')")
    public MembershipPrice getMembershipPrice()
    {
        return membershipPriceService.getMembershipPrice();
    }

    @PostMapping(Routes.MEMBERSHIPS_BASE+"/price")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public ResponseEntity<MembershipPrice> setMembershipPrice(@RequestBody MembershipPrice membershipPrice)
    {
        return membershipPriceService.setMembershipPrice(membershipPrice);
    }
}