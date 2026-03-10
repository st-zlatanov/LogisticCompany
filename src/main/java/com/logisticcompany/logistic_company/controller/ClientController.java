package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.service.ClientService;
import com.logisticcompany.logistic_company.service.ShipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ShipmentService shipmentService;

    public ClientController(ClientService clientService, ShipmentService shipmentService) {
        this.clientService = clientService;
        this.shipmentService = shipmentService;
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
    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable Long id, Model model) {

        Client client = clientService.getClientById(id);

        model.addAttribute("client", client);

        return "edit-client";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(@PathVariable Long id,
                               @ModelAttribute Client client) {

        clientService.updateClient(id, client);

        return "redirect:/clients";
    }

    @GetMapping("/{id}")
    public String clientDetails(@PathVariable Long id, Model model){

        Client client = clientService.getClientById(id);

        model.addAttribute("client", client);
        model.addAttribute("sentShipments",
                shipmentService.getShipmentsSentByClient(client));

        model.addAttribute("receivedShipments",
                shipmentService.getShipmentsReceivedByClient(client));

        return "client-details";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id){

        clientService.deleteOrDeactivateClient(id);

        return "redirect:/clients";
    }

    @PostMapping("/activate/{id}")
    public String activateClient(@PathVariable Long id){

        clientService.activateClient(id);

        return "redirect:/clients";
    }
}