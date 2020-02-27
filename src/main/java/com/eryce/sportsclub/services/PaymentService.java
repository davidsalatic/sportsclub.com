package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Payment;
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

    public List<Payment> getAllPaymentsInMonth(Integer year,Integer month) {
        return paymentRepository.findAllByYearOfPaymentAndMonthOfPayment(year,month);
    }

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
}