package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.MembershipPrice;
import com.eryce.sportsclub.repositories.MembershipPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipPriceService {

    @Autowired
    private MembershipPriceRepository membershipPriceRepository;

    private final int DEFAULT_ID=997;

    public ResponseEntity<MembershipPrice> setMembershipPrice(MembershipPrice membershipPrice) {
        if(membershipPriceRepository.findAll().isEmpty())
        {
            membershipPrice.setId(DEFAULT_ID);
            membershipPriceRepository.save(membershipPrice);
        }
        else
        {
            MembershipPrice existingPrice = membershipPriceRepository.getOne(DEFAULT_ID);
            existingPrice.setPrice(membershipPrice.getPrice());
            membershipPriceRepository.save(existingPrice);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public Integer getMembershipPrice() {
        MembershipPrice membershipPrice = membershipPriceRepository.getOne(DEFAULT_ID);
        return membershipPrice.getPrice();
    }
}
