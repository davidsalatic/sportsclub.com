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

    public ResponseEntity<MembershipPrice> setMembershipPrice(Integer price) {
        if(membershipPriceRepository.findAll().isEmpty())
        {
            MembershipPrice membershipPrice = new MembershipPrice();
            membershipPrice.setId(DEFAULT_ID);
            membershipPrice.setPrice(price);
            membershipPriceRepository.save(membershipPrice);
        }
        else
        {
            MembershipPrice membershipPrice = membershipPriceRepository.getOne(DEFAULT_ID);
            membershipPrice.setPrice(price);
            membershipPriceRepository.save(membershipPrice);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public MembershipPrice getMembershipPrice() {
        return membershipPriceRepository.getOne(DEFAULT_ID);
    }
}
