package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.ClubMember;
import com.eryce.sportsclub.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership,Integer> {
}
