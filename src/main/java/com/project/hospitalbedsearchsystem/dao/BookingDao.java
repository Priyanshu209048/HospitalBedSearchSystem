package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {
}
