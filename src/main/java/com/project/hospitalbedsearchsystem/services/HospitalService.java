package com.project.hospitalbedsearchsystem.services;

import com.project.hospitalbedsearchsystem.payloads.HospitalDTO;

import java.util.List;

public interface HospitalService {

    HospitalDTO saveHospital(HospitalDTO HospitalDTO);
    HospitalDTO updateHospital(String id, HospitalDTO HospitalDTO);
    HospitalDTO getHospitalById(String id);
    HospitalDTO getHospitalByEmail(String email);
    List<HospitalDTO> getAllHospitals();
    void deleteHospital(String id);
    Boolean isHospitalExistById(String id);
    Boolean isHospitalExistByEmail(String email);
    
}
