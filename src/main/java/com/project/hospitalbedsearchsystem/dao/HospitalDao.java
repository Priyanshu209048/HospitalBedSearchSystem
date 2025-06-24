package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalDao extends JpaRepository<Hospital, String> {

    Boolean existsByEmail(String email);
    Hospital findByEmail(String email);

}
