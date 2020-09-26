package com.eryce.sportsclub.services;

import com.eryce.sportsclub.configuration.ApplicationValues;
import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.dto.PeriodDto;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.repositories.MembershipRepository;
import com.eryce.sportsclub.repositories.PaymentRepository;
import com.eryce.sportsclub.repositories.PeriodRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@Service
@AllArgsConstructor
public class PeriodService {

    private PeriodRepository periodRepository;
    private AppUserService appUserService;
    private MailService mailService;
    private MembershipRepository membershipRepository;
    private PaymentRepository paymentRepository;
    private ApplicationValues applicationValues;
    private MembershipService membershipService;

    public List<PeriodDto> getAll() {
        return convertToDto(periodRepository.findAll());
    }

    private List<PeriodDto> convertToDto(List<Period> periods) {
        List<PeriodDto> periodsDto = new ArrayList<>();
        for (Period period : periods) {
            periodsDto.add(period.convertToDto());
        }
        return periodsDto;
    }

    public PeriodDto getById(Integer id) {
        return periodRepository.getOne(id).convertToDto();
    }

    public PeriodDto getByMonthAndYear(Integer month, Integer year) {
        Period period = periodRepository.findByMonthAndYear(month, year);
        if (period == null) {
            throw new EntityNotFoundException();
        }
        return period.convertToDto();
    }

    private PeriodDto insert(PeriodDto periodDto) {
        Period period = periodRepository.save(periodDto.convertToEntity());
        return period.convertToDto();
    }

    //each 1st and 2nd day in month at 00:00
    @Scheduled(cron = "0 0 0 1-2 * *")
    public void createPeriodIfNotExist() {
        if (getPeriodForCurrentMonth() == null) {
            Period period = Period.builder()
                    .month(now().getMonthValue())
                    .year(now().getYear())
                    .notifiedManagersOfMembershipDebts(false)
                    .build();
            insert(period.convertToDto());
            membershipService.createMembershipForCurrentPeriod();
        }
    }

    public Period getPeriodForCurrentMonth() {
        int currentMonth = now().getMonthValue();
        int currentYear = now().getYear();
        return periodRepository.findByMonthAndYear(currentMonth, currentYear);
    }

    //every day at 12:00
    @Scheduled(cron = "0 0 12 * * *")
    public void notifyManagersOfUnpaidMemberships() {
        Period period = getPeriodForCurrentMonth();

        if (!managersAlreadyNotified(period) && isDeadlineForMembershipsExpired()) {
            List<AppUserDto> membersWithInsufficientPayments = getMembersWithInsufficientPaymentsForMembership(period);

            mailService.sendUnpaidMembershipsListMessageToStaff(appUserService.getEmailsOfUsers(appUserService.getAllStaff()), membersWithInsufficientPayments);
            period.setNotifiedManagersOfMembershipDebts(true);
            periodRepository.save(period);
        }
    }

    private boolean managersAlreadyNotified(Period period) {
        return period.isNotifiedManagersOfMembershipDebts();
    }

    private boolean isDeadlineForMembershipsExpired() {
        int membershipDeadline = Integer.parseInt(applicationValues.getMembershipDeadline());
        return now().getDayOfMonth() >= membershipDeadline;
    }

    private List<AppUserDto> getMembersWithInsufficientPaymentsForMembership(Period period) {
        List<AppUserDto> membersWithInsufficientPayments = new ArrayList<>();

        Membership membership = membershipRepository.findByPeriod(period);
        for (AppUserDto member : appUserService.getAllMembers()) {
            if (!totalPaymentsIsSufficientForMembershipPrice(member, membership)) {
                membersWithInsufficientPayments.add(member);
                mailService.sendUnpaidMembershipMessageToMember(member.getUsername());
            }
        }
        return membersWithInsufficientPayments;
    }

    private boolean totalPaymentsIsSufficientForMembershipPrice(AppUserDto member, Membership membership) {
        return getTotalAmountPaidForMembershipByUser(member, membership) >= membership.getPrice();
    }

    private int getTotalAmountPaidForMembershipByUser(AppUserDto member, Membership membership) {
        int total = 0;

        for (Payment payment : paymentRepository.findAllByMembershipAndAppUser(membership, member.convertToEntity())) {
            total += payment.getAmount();
        }
        return total;
    }
}
