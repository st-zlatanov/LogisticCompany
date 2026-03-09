package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("client", new Client());
        return "create-client";
    }

    @PostMapping("/create")
    public String createClient(@ModelAttribute Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }
}