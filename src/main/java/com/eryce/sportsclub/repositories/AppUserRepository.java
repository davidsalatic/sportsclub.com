package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    List<AppUser> findAllByUsernameContainingIgnoreCase(String username);
}
