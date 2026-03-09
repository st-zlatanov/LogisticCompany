package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.dto.UserRegisterDTO;
import com.logisticcompany.logistic_company.model.Role;
import com.logisticcompany.logistic_company.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegisterDTO dto, Model model) {

        try {
            userService.register(dto);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}