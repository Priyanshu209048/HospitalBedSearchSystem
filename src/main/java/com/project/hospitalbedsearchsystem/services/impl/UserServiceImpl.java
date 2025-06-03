package com.project.hospitalbedsearchsystem.services.impl;

import com.project.hospitalbedsearchsystem.dao.UserDao;
import com.project.hospitalbedsearchsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public Boolean isUserExistById(Integer id) {
        return userDao.existsById(id);
    }

    @Override
    public Boolean isUserExistByEmail(String email) {
        return userDao.existsByEmail(email);
    }
}
