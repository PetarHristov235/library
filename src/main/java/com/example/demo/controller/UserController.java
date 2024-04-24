package com.example.demo.controller;

import com.example.demo.db.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {



    @GetMapping("/listUsers")
    public ModelAndView listUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
//        List<UserEntity> users = userService.findAllUsers();
//        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView showProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


//        UserEntity currentUser = UserService.getByUsername(username);
//
        ModelAndView modelAndView = new ModelAndView("profile");
//        modelAndView.addObject("userProfile", currentUser);

        return modelAndView;
    }
}