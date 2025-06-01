package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyRequestDao extends JpaRepository<EmergencyRequest, Integer> {
}
