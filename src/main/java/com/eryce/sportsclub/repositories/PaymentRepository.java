package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Integer> {
}
