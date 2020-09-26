package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsernameIgnoreCase(String username);

    List<AppUser> findAllByMemberGroup(MemberGroup memberGroup);

    AppUser findByJmbgIgnoreCase(String jmbg);

    List<AppUser> findAllByRole(Role role);

    List<AppUser> findAllByMemberGroupAndRole(MemberGroup memberGroup, Role role);
}
