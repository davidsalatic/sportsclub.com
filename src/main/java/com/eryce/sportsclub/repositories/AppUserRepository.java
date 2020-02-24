package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
}
