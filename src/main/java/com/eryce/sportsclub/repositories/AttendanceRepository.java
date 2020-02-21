package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
}
