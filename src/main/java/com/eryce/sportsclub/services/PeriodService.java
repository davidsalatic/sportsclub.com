package com.eryce.sportsclub.services;

import com.eryce.sportsclub.configuration.ApplicationValues;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.repositories.MembershipRepository;
import com.eryce.sportsclub.repositories.PaymentRepository;
import com.eryce.sportsclub.repositories.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ApplicationValues applicationValues;
    @Autowired
    private MembershipService membershipService;

    public List<Period> getAll() {
        return periodRepository.findAll();
    }

    public Period getById(Integer id) {
        return periodRepository.getOne(id);
    }

    public Period getByMonthAndYear(Integer month, Integer year) {
        return periodRepository.findByMonthAndYear(month,year);
    }

    private ResponseEntity<Period> insert(Period period) {
        this.periodRepository.save(period);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //each 1st and 2nd day in month at 00:00
    @Scheduled(cron = "0 0 0 1-2 * *")
    public void createPeriodIfNotExist()
    {
        if(getPeriodForCurrentMonth()==null)
        {
            Period period = new Period();
            period.setMonth(LocalDate.now().getMonthValue());
            period.setYear(LocalDate.now().getYear());
            period.setNotifiedManagersOfMembershipDebts(false);
            this.insert(period);
            membershipService.createMembershipForCurrentPeriod();
        }
    }

    public Period getPeriodForCurrentMonth()
    {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        return periodRepository.findByMonthAndYear(currentMonth,currentYear);
    }

    //every day at 12:00
    @Scheduled(cron = "0 0 12 * * *")
    public void notifyManagersOfUnpaidMemberships() {
        Period period = this.getPeriodForCurrentMonth();

        if(!managersAlreadyNotified(period) && isDeadlineForMembershipsExpired())
        {
            //members who do not have enough payments to settle membership debt
            List<AppUser> membersWithInsufficientPayments = getMembersWithInsufficientPaymentsForMembership(period);

            mailService.sendUnpaidMembershipsListMessageToStaff(appUserService.getEmails(appUserService.getAllStaff()),membersWithInsufficientPayments);
            period.setNotifiedManagersOfMembershipDebts(true);
            periodRepository.save(period);
        }
    }

    private boolean managersAlreadyNotified(Period period) {
        return period.getNotifiedManagersOfMembershipDebts();
    }

    private boolean isDeadlineForMembershipsExpired() {
        int membershipDeadline = Integer.parseInt(applicationValues.getMembershipDeadline());
        return LocalDate.now().getDayOfMonth()>=membershipDeadline;
    }

    private List<AppUser> getMembersWithInsufficientPaymentsForMembership(Period period) {

        List<AppUser> membersWithInsufficientPayments = new ArrayList<>();

        Membership membership = membershipRepository.findByPeriod(period);
        for(AppUser member : appUserService.getAllMembers())
            if(!totalPaymentsIsSufficientForMembershipPrice(member,membership))
            {
                membersWithInsufficientPayments.add(member);
                mailService.sendUnpaidMembershipMessageToMember(member.getUsername());
            }

        return membersWithInsufficientPayments;
    }

    private boolean totalPaymentsIsSufficientForMembershipPrice(AppUser member,Membership membership) {
        return getTotalAmountPaidForMembershipByUser(member,membership)>=membership.getPrice();
    }

    private int getTotalAmountPaidForMembershipByUser(AppUser member, Membership membership) {
        int total=0;

        for(Payment p : paymentRepository.findAllByMembershipAndAppUser(membership,member))
            total+=p.getAmount();
        return total;
    }
}
