package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    List<AppUser> findAllByUsernameIgnoreCase(String username);

    List<AppUser> findAllByMemberGroup(MemberGroup memberGroup);

    List<AppUser> findAllByJmbgIgnoreCase(String jmbg);

    AppUser findByUsernameAndPassword(String username, String password);

    List<AppUser> findAllByRole(Role memberRole);
}
