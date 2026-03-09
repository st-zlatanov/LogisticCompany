package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.dto.UserRegisterDTO;
import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.model.Role;
import com.logisticcompany.logistic_company.model.User;
import com.logisticcompany.logistic_company.repository.ClientRepository;
import com.logisticcompany.logistic_company.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ClientRepository clientRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User register(UserRegisterDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if(dto.getRole().equals("ADMIN")){
            throw new RuntimeException("Admin cannot be registered");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole()));
        user.setFullName(dto.getFullName());


        if (dto.getRole().equals("CLIENT")) {
            user.setEnabled(true);
        } else if (dto.getRole().equals("EMPLOYEE")) {
            user.setEnabled(false);
        }

        user = userRepository.save(user);

        if (dto.getRole().equals("CLIENT")) {
        Client client = new Client();
        client.setUser(user);
        client.setName(dto.getFullName());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());

        clientRepository.save(client);
        }
        return user;
    }

    public void makeAdmin(Long userId){

        User user = userRepository.findById(userId).orElseThrow();

        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    public void removeAdmin(Long id){

        User user = userRepository.findById(id).orElseThrow();

        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);

    }

    public List<User> getPendingEmployees() {
        return userRepository.findByRoleAndEnabled(Role.EMPLOYEE, false);
    }
}