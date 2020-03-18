package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByNameIgnoreCase(String name);

    Role findByNameContainingIgnoreCase(String roleName);
}
