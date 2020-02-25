package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments/{year}/{month}")
    public List<Payment> getAllPaymentsInMonth(@PathVariable("year")Integer year,@PathVariable("month")Integer month)
    {
        return paymentService.getAllPaymentsInMonth(year,month);
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

    @DeleteMapping("/payments")
    public ResponseEntity<Payment> deletePayment(@RequestBody Payment payment)
    {
        return paymentService.deletePayment(payment);
    }
}