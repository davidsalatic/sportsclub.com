package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.MembershipPriceDto;
import com.eryce.sportsclub.services.MembershipPriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.eryce.sportsclub.constants.Authorize.HAS_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.MEMBERSHIPS_BASE;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(MEMBERSHIPS_BASE)
@PreAuthorize(HAS_MANAGER_ROLE)
@AllArgsConstructor
public class MembershipPriceController {

    private MembershipPriceService membershipPriceService;

    @GetMapping("/price")
    public ResponseEntity<MembershipPriceDto> getMembershipPrice() {
        return ok(membershipPriceService.getMembershipPrice());
    }

    @PostMapping("/price")
    public ResponseEntity<MembershipPriceDto> setMembershipPrice(@RequestBody MembershipPriceDto membershipPriceDto) {
        return ok(membershipPriceService.setMembershipPrice(membershipPriceDto));
    }
}