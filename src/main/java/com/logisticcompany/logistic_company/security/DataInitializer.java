package com.logisticcompany.logistic_company.security;

import com.logisticcompany.logistic_company.model.Role;
import com.logisticcompany.logistic_company.model.User;
import com.logisticcompany.logistic_company.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("test"));
                admin.setRole(Role.ADMIN);
                admin.setFullName("System Admin");
                admin.setEnabled(true);

                userRepository.save(admin);
            }

        };
    }
}