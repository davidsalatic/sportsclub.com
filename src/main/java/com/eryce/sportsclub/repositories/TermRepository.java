package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term,Integer> {
}
