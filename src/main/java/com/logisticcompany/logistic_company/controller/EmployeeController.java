package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.dto.EmployeeCreateDTO;
import com.logisticcompany.logistic_company.model.Employee;
import com.logisticcompany.logistic_company.model.Position;
import com.logisticcompany.logistic_company.service.EmployeeService;
import com.logisticcompany.logistic_company.service.OfficeService;
import com.logisticcompany.logistic_company.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final OfficeService officeService;
    private final UserService userService;

    public EmployeeController(EmployeeService employeeService,
                              OfficeService officeService,
                              UserService userService) {
        this.employeeService = employeeService;
        this.officeService = officeService;
        this.userService = userService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("employee", new EmployeeCreateDTO());
        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("users", userService.getAllUsers());

        return "create-employee";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeCreateDTO dto) {

        employeeService.create(dto);
        return "redirect:/employees";
    }
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model){

        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);
        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("positions", Position.values());

        return "edit-employee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @RequestParam String position,
                                 @RequestParam Long officeId){

        employeeService.updateEmployee(id, position, officeId);

        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id){

        employeeService.deleteEmployee(id);

        return "redirect:/employees";
    }
}