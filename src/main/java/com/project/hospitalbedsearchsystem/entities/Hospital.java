package com.project.hospitalbedsearchsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    private String id;

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String specialization;

    private int locationId;
}

