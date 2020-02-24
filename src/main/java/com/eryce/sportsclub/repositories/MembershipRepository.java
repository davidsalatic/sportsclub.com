package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MembershipRepository extends JpaRepository<ClubMember,Integer> {
}
