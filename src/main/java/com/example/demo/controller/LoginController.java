package com.example.demo.controller;

import com.example.demo.db.entity.UserEntity;
import com.example.demo.db.entity.enums.Role;
import com.example.demo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView index = new ModelAndView("index");
        List<BooksEntity> books = service.findAllBooks();
        index.addObject("books", books);
        return index;
    }

    @PostMapping("/registerProcessing")
    public String registerProcessing(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("email") String email,
                                     @RequestParam("name") String name) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setActive(true);
        user.setRoles(Role.USER);

        userRepository.save(user);

        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @PostMapping("/loginProcessing")
    public String loginProcessing(@RequestParam("username") String username, @RequestParam("password") String password,
                                  Model model) {
        Boolean authenticationSuccessful = userRepository.existByUsernameAndPassword(username, passwordEncoder.encode(password));
        if (authenticationSuccessful) {
            return "redirect:/index"; // Redirect to home page after successful login
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login"; // Redirect back to login page if authentication fails
        }
    }
}
