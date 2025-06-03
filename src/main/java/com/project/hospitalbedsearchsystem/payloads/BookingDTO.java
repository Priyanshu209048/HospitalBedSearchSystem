package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private int id;
    private LocalDateTime bookingTime;
    private boolean active;
    private int patientId;
    private int bedId;
    private HospitalDTO hospitalDTO;
}
