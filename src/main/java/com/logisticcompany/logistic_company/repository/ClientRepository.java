package com.logisticcompany.logistic_company.repository;

import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
}