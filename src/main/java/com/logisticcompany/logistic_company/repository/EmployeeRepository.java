package com.logisticcompany.logistic_company.repository;

import com.logisticcompany.logistic_company.model.Employee;
import com.logisticcompany.logistic_company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser(User user);
    Optional<Employee> findByUserUsername(String username);
}