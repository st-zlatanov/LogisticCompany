package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.dto.EmployeeCreateDTO;
import com.logisticcompany.logistic_company.model.Employee;
import com.logisticcompany.logistic_company.model.Office;
import com.logisticcompany.logistic_company.model.Position;
import com.logisticcompany.logistic_company.model.User;
import com.logisticcompany.logistic_company.repository.EmployeeRepository;
import com.logisticcompany.logistic_company.repository.OfficeRepository;
import com.logisticcompany.logistic_company.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           OfficeRepository officeRepository,
                           UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
    }

    public Employee getEmployeeById(Long id) {

        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee create(EmployeeCreateDTO dto) {

        Office office = officeRepository
                .findById(dto.getOfficeId())
                .orElseThrow();

        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow();

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setPosition(Position.valueOf(dto.getPosition()));
        employee.setOffice(office);
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

    public void approveEmployee(Long userId, Long officeId, Position position) {

        User user = userRepository.findById(userId).orElseThrow();
        Office office = officeRepository.findById(officeId).orElseThrow();

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setName(user.getFullName());
        employee.setOffice(office);
        employee.setPosition(position);

        employeeRepository.save(employee);

        user.setEnabled(true);
        userRepository.save(user);
    }
    public void updateEmployee(Long id, String position, Long officeId){

        Employee employee = employeeRepository.findById(id).orElseThrow();

        employee.setPosition(Position.valueOf(position));

        Office office = officeRepository.findById(officeId).orElseThrow();

        employee.setOffice(office);

        employeeRepository.save(employee);

    }
    public void deleteEmployee(Long id){

        employeeRepository.deleteById(id);

    }
    public List<Employee> getEmployeesByOffice(Office office){
        return employeeRepository.findByOffice(office);
    }
}