package com.project.hospitalbedsearchsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String details;
    private boolean resolved;
    private LocalDateTime requestTime;

    private int patientId;
    private int locationId;
}

