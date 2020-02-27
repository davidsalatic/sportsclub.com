package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupRepository extends JpaRepository<MemberGroup,Integer> {

    List<MemberGroup> findAllByNameContainingIgnoreCase(String name);

}
