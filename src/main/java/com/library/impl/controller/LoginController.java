package com.library.impl.controller;

import com.library.impl.db.entity.UserEntity;
import com.library.impl.db.entity.enums.Role;
import com.library.impl.db.repository.UserRepository;
import com.library.impl.service.UserService;
import com.library.impl.util.DataValidation;
import com.library.impl.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/loginProcessing")
    public String loginProcessing(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        if(userRepository.existByUsernameAndPassword(username, passwordEncoder.encode(password))){
            return "redirect:/";
        }else{
            return "redirect:/login";
        }
    }


    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("register",
                "user",
                new UserEntity());
    }

    @PostMapping("/registerProcessing")
    public ModelAndView registerProcessing(@ModelAttribute("user") UserEntity user, BindingResult result) {
        validateData(user, result);

        if (result.hasErrors()) {
            return new ModelAndView("register", "user", user);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Role.USER);

        userRepository.save(user);
        emailService.registrationConfirmationEmail(user.getEmail(), user.getName());

        return new ModelAndView("redirect:/login");
    }

    private void validateData(UserEntity user, BindingResult result){
        if (DataValidation.isValidUsername(user.getUsername())) {
            result.rejectValue("username",
                    "invalid.username",
                    "Невалидно потребителско име!");
        }

        if (DataValidation.isValidName(user.getName())) {
            result.rejectValue("name",
                    "invalid.firstName",
                    "Невалидно име!");
        }

        if (DataValidation.isValidEmail(user.getEmail())) {
            result.rejectValue("email",
                    "invalid.email",
                    "Невалиден имейл!");
        }

        if (DataValidation.isValidPassword(user.getPassword())) {
            result.rejectValue("password",
                    "invalid.password",
                    "Паролата трябва да съдържа поне 5 символа!");
        }
        if (userService.existsUserEmail(user.getEmail())) {
            result.rejectValue("email",
                    "duplicate.email",
                    "Имейлът е зает!");
        }
        if (userService.existsUsername(user.getUsername())) {
            result.rejectValue("username",
                    "duplicate.username",
                    "Потребителското име е заето!");
        }
    }
}