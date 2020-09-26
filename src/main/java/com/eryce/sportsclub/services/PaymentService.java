package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.PaymentDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MembershipRepository;
import com.eryce.sportsclub.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentRepository paymentRepository;
    private AppUserRepository appUserRepository;
    private MembershipRepository membershipRepository;

    public List<PaymentDto> getAllPaymentsForMembershipByAppUser(Integer membershipId, Integer appUserId) {
        Membership membership = membershipRepository.getOne(membershipId);
        AppUser appUser = appUserRepository.getOne(appUserId);
        return convertToDto(paymentRepository.findAllByMembershipAndAppUser(membership, appUser));
    }

    public List<PaymentDto> getAllPaymentsForMembership(Integer membershipId) {
        Membership membership = membershipRepository.getOne(membershipId);
        return convertToDto(paymentRepository.findAllByMembership(membership));
    }

    public List<PaymentDto> getAllPaymentsForAppUser(Integer memberId) {
        AppUser appUser = appUserRepository.getOne(memberId);
        return convertToDto(paymentRepository.findAllByAppUser(appUser));
    }

    public List<PaymentDto> convertToDto(List<Payment> paymentList) {
        List<PaymentDto> paymentsDto = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentsDto.add(payment.convertToDto());
        }
        return paymentsDto;
    }

    public PaymentDto getById(Integer id) {
        return paymentRepository.getOne(id).convertToDto();
    }

    public PaymentDto insert(PaymentDto paymentDto) {
        Payment payment = paymentRepository.save(paymentDto.convertToEntity());
        return payment.convertToDto();
    }

    public PaymentDto update(PaymentDto paymentDto) {
        return insert(paymentDto);
    }

    public void delete(Integer id) {
        Payment payment = paymentRepository.getOne(id);
        paymentRepository.delete(payment);
    }
}