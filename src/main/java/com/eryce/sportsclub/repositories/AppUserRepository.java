package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    List<AppUser> findAllByUsernameContainingIgnoreCase(String username);

    List<AppUser> findAllByNameOrSurnameContainingIgnoreCase(String name, String surname);

    List<AppUser> findAllByMemberGroup(MemberGroup memberGroup);
}
