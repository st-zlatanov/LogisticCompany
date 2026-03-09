package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.model.Position;
import com.logisticcompany.logistic_company.service.EmployeeService;
import com.logisticcompany.logistic_company.service.OfficeService;
import com.logisticcompany.logistic_company.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final OfficeService officeService;
    private final EmployeeService employeeService;

    public AdminController(UserService userService,
                           OfficeService officeService,
                           EmployeeService employeeService) {
        this.userService = userService;
        this.officeService = officeService;
        this.employeeService = employeeService;
    }

    @GetMapping("/pending-employees")
    public String pendingEmployees(Model model) {
        model.addAttribute("users", userService.getPendingEmployees());
        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("positions", Position.values());
        return "pending-employees";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam Long userId,
                          @RequestParam Long officeId,
                          @RequestParam Position position) {

        employeeService.approveEmployee(userId, officeId, position);

        return "redirect:/admin/pending-employees";
    }

    @GetMapping("/users")
    public String users(Model model){

        model.addAttribute("users", userService.getAllUsers());

        return "admin-users";
    }

    @PostMapping("/make-admin/{id}")
    public String makeAdmin(@PathVariable Long id){

        userService.makeAdmin(id);

        return "redirect:/admin/users";
    }

    @PostMapping("/remove-admin/{id}")
    public String removeAdmin(@PathVariable Long id){

        userService.removeAdmin(id);

        return "redirect:/admin/users";
    }
}