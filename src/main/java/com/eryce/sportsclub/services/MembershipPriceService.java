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

    public ResponseEntity<MembershipPrice> setMembershipPrice(Integer price) {
        if(membershipPriceRepository.findAll().isEmpty())
        {
            MembershipPrice membershipPrice = new MembershipPrice();
            membershipPrice.setId(997);
            membershipPrice.setPrice(price);
            membershipPriceRepository.save(membershipPrice);
        }
        else
        {
            MembershipPrice membershipPrice = membershipPriceRepository.getOne(997);
            membershipPrice.setPrice(price);
            membershipPriceRepository.save(membershipPrice);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public List<MembershipPrice> getMembershipPrice() {
        return membershipPriceRepository.findAll();
    }
}
