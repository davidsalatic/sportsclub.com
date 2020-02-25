package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership,Integer> {

//    List<Membership> findAllByAppUser(AppUser appUser);
//    List<Membership> findAllByYearAndMonth(Integer year,Integer month);

}
