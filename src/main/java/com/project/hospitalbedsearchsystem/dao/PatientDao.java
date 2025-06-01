package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends JpaRepository<Patient, String> {
}
