package com.logisticcompany.logistic_company.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Model model) {
        model.addAttribute("message", "Възникна неочаквана грешка");
        return "error";
    }
}