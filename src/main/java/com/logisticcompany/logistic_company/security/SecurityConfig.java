package com.logisticcompany.logistic_company.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/shipments/track/**").permitAll()
                        .requestMatchers("/shipments/create").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/shipments/deliver/**").hasAnyRole("ADMIN","EMPLOYEE")
                        .requestMatchers("/shipments/my").hasRole("CLIENT")
                        .requestMatchers("/shipments/**").authenticated()

                        .requestMatchers("/clients/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/offices/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}