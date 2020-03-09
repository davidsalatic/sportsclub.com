package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments/membership/{membershipId}/user/{userId}")
    public List<Payment> getAllPaymentsForMembershipByAppUser(@PathVariable("membershipId") Integer membershipId,@PathVariable("userId")Integer userId)
    {
        return paymentService.getAllPaymentsForMembershipByAppUser(membershipId,userId);
    }

    @GetMapping("/payments/membership/{membershipId}")
    public List<Payment> getAllPaymentsForMembership(@PathVariable("membershipId") Integer membershipId)
    {
        return paymentService.getAllPaymentsForMembership(membershipId);
    }

    @GetMapping("/payments/{id}")
    public Payment getById(@PathVariable("id")Integer id){
        return paymentService.getById(id);
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> insert(@RequestBody Payment payment)
    {
        return paymentService.insert(payment);
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Payment> delete(@PathVariable Integer id)
    {
        return paymentService.delete(id);
    }
}