package com.library.impl.controller;

import com.library.impl.db.entity.OrderEntity;
import com.library.impl.db.entity.UserEntity;
import com.library.impl.service.OrderService;
import com.library.impl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/listUsers")
    public ModelAndView listUsers() {
        List<UserEntity> users = userService.findAllUsers();

        return new ModelAndView("users",
                "users",
                users);
    }

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity currentUser = userService.getUserByUsername(username);
        List<OrderEntity> userOrders = orderService.getOrdersByUsername(username);

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userProfile", currentUser);
        modelAndView.addObject("userOrders", userOrders);

        return modelAndView;
    }

    @GetMapping(value="/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ModelAndView("redirect:/listUsers");
    }

    @GetMapping(value="/banUser/{id}")
    public ModelAndView banUser(@PathVariable Long id) {
        userService.banUser(id);

        return new ModelAndView("redirect:/listUsers");
    }
    @GetMapping(value="/activateUser/{id}")
    public ModelAndView activateUser(@PathVariable Long id) {
        userService.activateUser(id);

        return new ModelAndView("redirect:/listUsers");
    }
}
