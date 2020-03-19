package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGroupRepository extends JpaRepository<MemberGroup,Integer> {
    MemberGroup findByName(String groupName);
}
