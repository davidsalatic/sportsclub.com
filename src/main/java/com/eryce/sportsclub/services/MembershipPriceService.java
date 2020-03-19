package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.MembershipPrice;
import com.eryce.sportsclub.repositories.MembershipPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MembershipPriceService {

    @Autowired
    private MembershipPriceRepository membershipPriceRepository;

    private final int DEFAULT_ID=997;

    public MembershipPrice getMembershipPrice() {
        if(priceExists())
            return membershipPriceRepository.getOne(DEFAULT_ID);
        else
            return createDefaultMembershipPrice();
    }

    private boolean priceExists()
    {
        return !(membershipPriceRepository.findAll().isEmpty());
    }

    private MembershipPrice createDefaultMembershipPrice() {
        MembershipPrice membershipPrice = new MembershipPrice();
        membershipPrice.setId(DEFAULT_ID);
        membershipPrice.setPrice(0);
        return membershipPrice;
    }

    public ResponseEntity<MembershipPrice> setMembershipPrice(MembershipPrice membershipPrice) {
        if(priceExists())
        {
            MembershipPrice existingPrice = getMembershipPrice();
            existingPrice.setPrice(membershipPrice.getPrice());
            membershipPriceRepository.save(existingPrice);
        }
        else
        {
            membershipPrice.setId(DEFAULT_ID);
            membershipPriceRepository.save(membershipPrice);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
