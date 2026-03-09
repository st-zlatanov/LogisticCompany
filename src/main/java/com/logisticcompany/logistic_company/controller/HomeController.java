package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.service.ShipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ShipmentService shipmentService;

    public HomeController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("totalShipments",
                shipmentService.getAllShipments().size());

        return "home";
    }
}