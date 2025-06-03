package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedDTO {
    private int id;
    private String type;
    private String diseaseTag;
    private int totalBeds;
    private int availableBeds;
    private HospitalDTO hospitalDTO;
}
