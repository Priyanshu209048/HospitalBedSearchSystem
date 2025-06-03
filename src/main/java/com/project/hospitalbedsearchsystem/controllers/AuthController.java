package com.project.hospitalbedsearchsystem.controllers;

import com.project.hospitalbedsearchsystem.components.JwtTokenHelper;
import com.project.hospitalbedsearchsystem.dao.UserDao;
import com.project.hospitalbedsearchsystem.entities.User;
import com.project.hospitalbedsearchsystem.exceptions.ResourceNotFoundException;
import com.project.hospitalbedsearchsystem.payloads.*;
import com.project.hospitalbedsearchsystem.services.PatientService;
import com.project.hospitalbedsearchsystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserDao userDao;
    private final PatientService patientService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);

        User user = this.userDao.findByEmail(userDetails.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "email", userDetails.getUsername()));

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setUser(this.modelMapper.map(user, UserDTO.class));
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled");
        }
    }

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerFarmer(@RequestBody @Valid PatientDTO patientDTO) {
        if (userService.isUserExistByEmail(patientDTO.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("User already exists"), HttpStatus.CONFLICT);
        }

        PatientDTO farmerRegistered = patientService.savePatient(patientDTO);
        return new ResponseEntity<>(farmerRegistered, HttpStatus.CREATED);
    }

    /*@PostMapping("/register/bank")
    public ResponseEntity<?> registerBank(@RequestBody @Valid HospitalDTO hospitalDTO) {
        if (this.hospitalService.isBankExistByEmail(hospitalDTO.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("Bank already exists !!"), HttpStatus.CONFLICT);
        }

        HospitalDTO bank = this.hospitalService.addBank(hospitalDTO);
        return new ResponseEntity<>(new ApiResponse("Bank registered successfully"), HttpStatus.CREATED);
    }*/

}
