package com.project.hospitalbedsearchsystem.services.impl;

import com.project.hospitalbedsearchsystem.constants.UsersConstants;
import com.project.hospitalbedsearchsystem.dao.HospitalDao;
import com.project.hospitalbedsearchsystem.dao.LocationDao;
import com.project.hospitalbedsearchsystem.dao.UserDao;
import com.project.hospitalbedsearchsystem.entities.Hospital;
import com.project.hospitalbedsearchsystem.entities.Location;
import com.project.hospitalbedsearchsystem.entities.User;
import com.project.hospitalbedsearchsystem.exceptions.ResourceNotFoundException;
import com.project.hospitalbedsearchsystem.payloads.HospitalDTO;
import com.project.hospitalbedsearchsystem.payloads.LocationDTO;
import com.project.hospitalbedsearchsystem.services.HospitalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private UserDao userDao;
    private HospitalDao hospitalDao;
    private ModelMapper modelMapper;
    private final LocationDao locationDao;
    private final GeoService geoService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public HospitalDTO saveHospital(HospitalDTO HospitalDTO) {
        return null;
    }

    @Override
    public HospitalDTO updateHospital(String id, HospitalDTO HospitalDTO) {
        return null;
    }

    @Override
    public HospitalDTO getHospitalById(String id) {
        return null;
    }

    @Override
    public HospitalDTO getHospitalByEmail(String email) {
        return null;
    }

    @Override
    public List<HospitalDTO> getAllHospitals() {
        List<Hospital> hospitalList = this.hospitalDao.findAll();
        return hospitalList.stream().map(hospital -> {
            HospitalDTO hospitalDTO = new HospitalDTO();
            Location location = this.locationDao.findById(hospital.getLocationId()).orElseThrow(() ->
                    new ResourceNotFoundException(UsersConstants.LOCATION, UsersConstants.ID, String.valueOf(hospital.getLocationId())));
            hospitalDTO.setLocationDTO(this.modelMapper.map(location, LocationDTO.class));
            return hospitalDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteHospital(String id) {
        Hospital hospital = this.hospitalDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.HOSPITAL, UsersConstants.ID, id));
        User user = this.userDao.findByEmail(hospital.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.USER, UsersConstants.EMAIL, hospital.getEmail()));
        this.hospitalDao.delete(hospital);
        this.userDao.delete(user);
    }

    @Override
    public Boolean isHospitalExistById(String id) {
        return this.hospitalDao.existsById(id);
    }

    @Override
    public Boolean isHospitalExistByEmail(String email) {
        return this.hospitalDao.existsByEmail(email);
    }
}
