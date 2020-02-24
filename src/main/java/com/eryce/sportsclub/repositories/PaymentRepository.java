package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
