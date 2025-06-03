package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyRequestDTO {
    private int id;
    private String details;
    private boolean resolved;
    private LocalDateTime requestTime;
    private PatientDTO patientDTO;
    private LocationDTO locationDTO;
}
