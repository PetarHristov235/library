package com.library.impl.service;

import com.library.impl.db.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserByUsername(String username);
    List<UserEntity> findAllUsers();
    void deleteUser(Long id);
    void banUser(Long id);
    void activateUser(Long id);
    boolean existsUsername(String username);
    boolean existsUserEmail(String email);

}
