package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.ClubMember;
import org.springframework.data.repository.CrudRepository;

public interface MembershipRepository extends CrudRepository<ClubMember,Integer> {
}
