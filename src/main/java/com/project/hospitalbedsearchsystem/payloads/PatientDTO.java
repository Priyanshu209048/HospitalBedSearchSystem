package com.project.hospitalbedsearchsystem.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private String id;
    private String fullName;
    private int age;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private String address;
    private LocationDTO locationDTO;
}