package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String specialization;
    private LocationDTO locationDTO;
}
