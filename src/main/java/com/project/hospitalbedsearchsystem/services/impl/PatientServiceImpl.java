package com.project.hospitalbedsearchsystem.services.impl;

import com.project.hospitalbedsearchsystem.constants.UsersConstants;
import com.project.hospitalbedsearchsystem.dao.LocationDao;
import com.project.hospitalbedsearchsystem.dao.PatientDao;
import com.project.hospitalbedsearchsystem.dao.UserDao;
import com.project.hospitalbedsearchsystem.entities.Location;
import com.project.hospitalbedsearchsystem.entities.Patient;
import com.project.hospitalbedsearchsystem.entities.User;
import com.project.hospitalbedsearchsystem.exceptions.ResourceNotFoundException;
import com.project.hospitalbedsearchsystem.payloads.LocationDTO;
import com.project.hospitalbedsearchsystem.payloads.PatientDTO;
import com.project.hospitalbedsearchsystem.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final LocationDao locationDao;
    private final GeoService geoService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) {
        if (patientDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        String password = bCryptPasswordEncoder.encode(patientDTO.getPassword());

        patientDTO.setId(UUID.randomUUID().toString());
        patientDTO.setPassword(password);

        Patient patient = modelMapper.map(patientDTO, Patient.class);
        Location location = this.geoService.getCoordinatesFromAddress(patientDTO.getAddress());
        locationDao.save(location);
        patient.setLocationId(location.getId());

        User user = new User();
        user.setEmail(patientDTO.getEmail());
        user.setPassword(password);
        user.setRole(UsersConstants.ROLE_PATIENT);
        userDao.save(user);

        Patient save = patientDao.save(patient);
        PatientDTO map = this.modelMapper.map(save, PatientDTO.class);
        map.setLocationDTO(this.modelMapper.map(location, LocationDTO.class));
        return map;
    }

    @Override
    public PatientDTO updatePatient(String id, PatientDTO patientDTO) {
        Patient patient = this.patientDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, id));

        patient.setFullName(patientDTO.getFullName());
        patient.setAge(patientDTO.getAge());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setEmail(patientDTO.getEmail());

        if (!patientDTO.getAddress().trim().equalsIgnoreCase(patient.getAddress().trim())) {
            Location newLocation = geoService.getCoordinatesFromAddress(patientDTO.getAddress());

            Location existingLocation = locationDao.findById(patient.getLocationId()).orElseThrow(() ->
                    new ResourceNotFoundException(UsersConstants.LOCATION, UsersConstants.ID, String.valueOf(patient.getLocationId())));

            existingLocation.setLatitude(newLocation.getLatitude());
            existingLocation.setLongitude(newLocation.getLongitude());
            locationDao.save(existingLocation);

            patient.setAddress(patientDTO.getAddress());
        }

        Patient update = patientDao.save(patient);

        return this.modelMapper.map(update, PatientDTO.class);
    }

    @Override
    public PatientDTO getPatientById(String id) {
        Patient patient = this.patientDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, id));
        PatientDTO patientDTO = this.modelMapper.map(patient, PatientDTO.class);
        Location location = this.locationDao.findById(patient.getLocationId()).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, String.valueOf(patient.getLocationId())));
        LocationDTO locationDTO = this.modelMapper.map(location, LocationDTO.class);
        patientDTO.setLocationDTO(locationDTO);
        return patientDTO;
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        Patient patient = this.patientDao.findByEmail(email);
        PatientDTO patientDTO = this.modelMapper.map(patient, PatientDTO.class);
        Location location = this.locationDao.findById(patient.getLocationId()).orElseThrow(() ->
                new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, String.valueOf(patient.getLocationId())));
        LocationDTO locationDTO = this.modelMapper.map(location, LocationDTO.class);
        patientDTO.setLocationDTO(locationDTO);

        return patientDTO;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patientList = this.patientDao.findAll();
        return patientList.stream().map(patient -> {
            PatientDTO patientDTO = this.modelMapper.map(patient, PatientDTO.class);
            Location location = this.locationDao.findById(patient.getLocationId()).orElseThrow(() ->
                    new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, String.valueOf(patient.getLocationId())));
            LocationDTO locationDTO = this.modelMapper.map(location, LocationDTO.class);
            patientDTO.setLocationDTO(locationDTO);

            return patientDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePatient(String id) {
        Patient patient = this.patientDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(UsersConstants.PATIENT, UsersConstants.ID, id));
        this.userDao.deleteByEmail(patient.getEmail());
        this.patientDao.deleteById(id);
    }

    @Override
    public Boolean isPatientExistById(String id) {
        return this.patientDao.existsById(id);
    }

    @Override
    public Boolean isPatientExistByEmail(String email) {
        return this.patientDao.existsByEmail(email);
    }
}
