package com.example.demo.service;

import com.example.demo.db.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserByUsername(String username);
    List<UserEntity> findAllUsers();
}
