package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    List<Payment> findAllByYearOfPaymentAndMonthOfPayment(Integer yearOfPayment,Integer monthOfPayment);

}
