package com.eryce.sportsclub.services;

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

    public ResponseEntity<Payment> insertPayment(Payment payment) {
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Payment> updatePayment(Payment payment) {
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Payment> deletePayment(Payment payment) {
        paymentRepository.delete(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Payment> getAllPaymentsForMembershipByAppUser(Integer membershipId, Integer appUserId) {
        Membership membership = membershipRepository.getOne(membershipId);
        AppUser appUser = appUserRepository.getOne(appUserId);
        return paymentRepository.findAllByMembershipAndAppUser(membership,appUser);
    }
}