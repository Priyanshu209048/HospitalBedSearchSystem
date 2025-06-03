package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

}
