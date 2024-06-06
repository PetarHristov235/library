package com.example.demo.security.confg;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/",
                                        "/login",
                                        "/register",
                                        "/registerProcessing",
                                        "/loginProcessing",
                                        "/books/sort",
                                        "/books/filter",
                                        "/books/*")
                                .permitAll()

                                .requestMatchers("/random",
                                        "/orderBook/*",
                                        "/confirmOrder/*",
                                        "/rateBook/*",
                                        "/saveRating")
                                .hasRole("USER")

                                .requestMatchers("/bookStock",
                                        "/addBook",
                                        "/saveBook",
                                        "/editBook/*",
                                        "/deleteBook/*",
                                        "/listOrders",
                                        "/deleteOrder/*",
                                        "/remind/*",
                                        "/showRates/*",
                                        "/listUsers",
                                        "/deleteUser/*",
                                        "/banUser/*",
                                        "/activateUser/*")
                                .hasRole("ADMIN")

                                .requestMatchers("/profile")
                                .authenticated()

                                .requestMatchers(
                                        antMatcher("/css/**")
                                ).permitAll()

                        .anyRequest().authenticated()
                )
                .logout(
                        logout -> logout
                                .logoutSuccessUrl("/")
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginProcessingUrl("/loginProcessing")
                                .defaultSuccessUrl("/", true)
                                .permitAll()).build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
