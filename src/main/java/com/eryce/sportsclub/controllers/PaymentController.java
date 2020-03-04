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

    @GetMapping("/payments/{id}")
    public Payment getById(@PathVariable("id")Integer id){
        return paymentService.getById(id);
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> insertPayment(@RequestBody Payment payment)
    {
        return paymentService.insertPayment(payment);
    }

    @PutMapping("/payments")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment)
    {
        return paymentService.updatePayment(payment);
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Payment> deletePayment(@PathVariable Integer id)
    {
        return paymentService.deletePayment(id);
    }
}