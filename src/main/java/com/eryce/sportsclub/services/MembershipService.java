package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.MembershipDto;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.repositories.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipService {

    private MembershipRepository membershipRepository;
    private MembershipPriceService priceService;

    @Autowired // field injection used because of circular bean dependency
    private PeriodService periodService;

    public MembershipService(MembershipRepository membershipRepository, MembershipPriceService membershipPriceService) {
        this.membershipRepository = membershipRepository;
        this.priceService = membershipPriceService;
    }

    public List<MembershipDto> getAll() {
        return convertToDto(membershipRepository.findAll());
    }

    private List<MembershipDto> convertToDto(List<Membership> memberships) {
        List<MembershipDto> membershipsDto = new ArrayList<>();
        for (Membership membership : memberships) {
            membershipsDto.add(membership.convertToDto());
        }
        return membershipsDto;
    }

    public MembershipDto getById(Integer id) {
        return membershipRepository.getOne(id).convertToDto();
    }

    public MembershipDto getByPeriod(Integer periodId) {
        Period period = periodService.getById(periodId).convertToEntity();
        return membershipRepository.findByPeriod(period).convertToDto();
    }

    public MembershipDto update(MembershipDto membershipDto) {
        return insert(membershipDto);
    }

    private MembershipDto insert(MembershipDto membershipDto) {
        Membership membership = membershipRepository.save(membershipDto.convertToEntity());
        return membership.convertToDto();
    }

    public void createMembershipForCurrentPeriod() {
        Period currentPeriod = periodService.getPeriodForCurrentMonth();
        if (!membershipExistsInPeriod(currentPeriod)) {
            MembershipDto membershipDto = MembershipDto.builder()
                    .period(currentPeriod.convertToDto())
                    .price(priceService.getMembershipPrice().getPrice())
                    .build();
            this.insert(membershipDto);
        }
    }

    private boolean membershipExistsInPeriod(Period period) {
        return membershipRepository.findByPeriod(period) != null;
    }
}
