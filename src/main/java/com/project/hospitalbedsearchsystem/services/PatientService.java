package com.project.hospitalbedsearchsystem.services;

import com.project.hospitalbedsearchsystem.payloads.PatientDTO;

import java.util.List;

public interface PatientService {

    PatientDTO savePatient(PatientDTO patientDTO);
    PatientDTO updatePatient(String id, PatientDTO patientDTO);
    PatientDTO getPatientById(String id);
    PatientDTO getPatientByEmail(String email);
    List<PatientDTO> getAllPatients();
    void deletePatient(String id);
    Boolean isPatientExistById(String id);
    Boolean isPatientExistByEmail(String email);

}
