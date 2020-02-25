package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    List<Permission> findAllByRole(Role role);
}
