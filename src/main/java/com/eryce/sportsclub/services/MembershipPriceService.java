package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.MembershipPriceDto;
import com.eryce.sportsclub.models.MembershipPrice;
import com.eryce.sportsclub.repositories.MembershipPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MembershipPriceService {

    private MembershipPriceRepository membershipPriceRepository;

    private final int DEFAULT_ID = 997;

    public MembershipPriceDto getMembershipPrice() {
        if (priceExists()) {
            return membershipPriceRepository.getOne(DEFAULT_ID).convertToDto();
        }
        return createDefaultMembershipPrice();
    }

    private boolean priceExists() {
        return !(membershipPriceRepository.findAll().isEmpty());
    }

    private MembershipPriceDto createDefaultMembershipPrice() {
        return MembershipPrice.builder()
                .id(DEFAULT_ID)
                .price(0)
                .build()
                .convertToDto();
    }

    public MembershipPriceDto setMembershipPrice(MembershipPriceDto membershipPriceDto) {
        if (priceExists()) {
            MembershipPriceDto existingPrice = getMembershipPrice();
            existingPrice.setPrice(membershipPriceDto.getPrice());
            MembershipPrice membershipPrice = membershipPriceRepository.save(existingPrice.convertToEntity());
            return membershipPrice.convertToDto();
        } else {
            membershipPriceDto.setId(DEFAULT_ID);
            MembershipPrice membershipPrice = membershipPriceRepository.save(membershipPriceDto.convertToEntity());
            return membershipPrice.convertToDto();
        }
    }
}
