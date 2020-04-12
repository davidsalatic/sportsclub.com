package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.PaymentRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MembershipRepository;
import com.eryce.sportsclub.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    public List<Payment> getAllPaymentsForMembershipByAppUser(Integer membershipId, Integer appUserId) {
        Membership membership = membershipRepository.getOne(membershipId);
        AppUser appUser = appUserRepository.getOne(appUserId);
        return paymentRepository.findAllByMembershipAndAppUser(membership,appUser);
    }

    public List<Payment> getAllPaymentsForMembership(Integer membershipId) {
        Membership membership = membershipRepository.getOne(membershipId);
        return paymentRepository.findAllByMembership(membership);
    }

    public List<Payment> getAllPaymentsForAppUser(Integer memberId) {
        AppUser appUser = appUserRepository.getOne(memberId);
        return paymentRepository.findAllByAppUser(appUser);
    }

    public Payment getById(Integer id) {
        return paymentRepository.getOne(id);
    }

    public ResponseEntity<Payment> insert(PaymentRequestDTO paymentRequestDTO) {
        paymentRepository.save(paymentRequestDTO.generatePayment());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Payment> update(PaymentRequestDTO paymentRequestDTO) {
        this.insert(paymentRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Payment> delete(Integer id) {
        paymentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}