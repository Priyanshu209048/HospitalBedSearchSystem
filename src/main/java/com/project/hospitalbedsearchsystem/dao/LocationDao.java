package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDao extends JpaRepository<Location, Integer> {
}
