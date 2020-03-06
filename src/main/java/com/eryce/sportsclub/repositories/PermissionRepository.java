package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
