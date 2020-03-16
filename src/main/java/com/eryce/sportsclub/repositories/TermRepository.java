package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term,Integer> {

    List<Term> findAllByMemberGroup(MemberGroup memberGroup);
}
