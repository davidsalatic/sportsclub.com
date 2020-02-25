package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
}
