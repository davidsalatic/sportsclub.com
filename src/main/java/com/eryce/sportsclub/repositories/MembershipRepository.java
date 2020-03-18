package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.models.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership,Integer> {
    Membership findByPeriod(Period period);
}
