package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MembershipRepository extends JpaRepository<Membership,Integer> {

    Membership findByMonthAndYear(Integer month, Integer year);
}
