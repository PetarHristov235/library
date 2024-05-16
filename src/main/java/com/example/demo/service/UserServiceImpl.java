package com.example.demo.service;

import com.example.demo.db.entity.UserEntity;
import com.example.demo.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> findAllUsers() {
     return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
    userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void banUser(Long id) {
        userRepository.findById(id).ifPresent(user -> user.setActive(false));
    }
}