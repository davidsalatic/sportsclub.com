package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    List<Attendance> findAllByAppUser(AppUser appUser);
    List<Attendance> findAllByTrainingSession(TrainingSession trainingSession);
    Attendance findByTrainingSessionAndAppUser(TrainingSession trainingSession, AppUser appUser);
}
