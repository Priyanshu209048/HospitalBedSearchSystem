package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private int id;
    private int rating;
    private String comments;
    private PatientDTO patientDTO;
    private HospitalDTO hospitalDTO;
}
