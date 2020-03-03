package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    List<Payment> findAllByAppUser(AppUser appUser);

    List<Payment> findAllByMembershipAndAppUser(Membership membership, AppUser appUser);
}
