package com.project.hospitalbedsearchsystem.services;

public interface UserService {

    Boolean isUserExistById(Integer id);
    Boolean isUserExistByEmail(String email);

}
