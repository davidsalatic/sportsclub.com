package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.repositories.MembershipRepository;
import com.eryce.sportsclub.repositories.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private MembershipPriceService priceService;

    public List<Membership> getAll() {

        return membershipRepository.findAll();
    }

    public Membership getByPeriod(Integer periodId) {
        Period period = periodService.getById(periodId);
        return membershipRepository.findByPeriod(period);
    }

    public Membership getById(Integer id) {
        return membershipRepository.getOne(id);
    }


    private ResponseEntity<Membership> insert(Membership membership) {
        membershipRepository.save(membership);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void createMembershipForCurrentPeriod()
    {
        Period currentPeriod = periodService.getPeriodForCurrentMonth();
        if(!membershipExistsInPeriod(currentPeriod))
        {
            Membership membership = new Membership();
            membership.setPeriod(currentPeriod);
            membership.setPrice(priceService.getMembershipPrice().getPrice());
            this.insert(membership);
        }
    }

    private boolean membershipExistsInPeriod(Period period) {
        return membershipRepository.findByPeriod(period)!=null;
    }

    public ResponseEntity<Membership> update(Membership membership) {
        return this.insert(membership);
    }

}
