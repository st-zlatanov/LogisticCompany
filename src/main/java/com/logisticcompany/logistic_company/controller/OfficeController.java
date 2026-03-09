package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.model.Office;
import com.logisticcompany.logistic_company.service.OfficeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("offices", officeService.getAllOffices());
        return "offices";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("office", new Office());
        return "create-office";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Office office) {
        officeService.save(office);
        return "redirect:/offices";
    }
}