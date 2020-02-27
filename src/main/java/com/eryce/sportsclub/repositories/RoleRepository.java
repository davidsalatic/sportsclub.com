package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role,Integer> {

    List<Role> findAllByNameContainingIgnoreCase(String name);
}
