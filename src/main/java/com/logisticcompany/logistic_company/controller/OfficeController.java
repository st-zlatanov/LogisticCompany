package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.model.Office;
import com.logisticcompany.logistic_company.service.EmployeeService;
import com.logisticcompany.logistic_company.service.OfficeService;
import com.logisticcompany.logistic_company.service.ShipmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final EmployeeService employeeService;
    private final ShipmentService shipmentService;

    public OfficeController(OfficeService officeService, EmployeeService employeeService, ShipmentService shipmentService) {
        this.officeService = officeService;
        this.employeeService = employeeService;
        this.shipmentService = shipmentService;
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
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

    @GetMapping("/edit/{id}")
    public String editOffice(@PathVariable Long id, Model model){

        Office office = officeService.getOfficeById(id);

        model.addAttribute("office", office);

        return "edit-office";
    }

    @PostMapping("/edit/{id}")
    public String updateOffice(@PathVariable Long id,
                               @ModelAttribute Office office){

        officeService.updateOffice(id, office);

        return "redirect:/offices";
    }

    @PostMapping("/delete/{id}")
    public String deleteOffice(@PathVariable Long id){

        officeService.deleteOffice(id);

        return "redirect:/offices";
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/{id}")
    public String officeDetails(@PathVariable Long id, Model model){

        Office office = officeService.getOfficeById(id);

        model.addAttribute("office", office);

        model.addAttribute("employees",
                employeeService.getEmployeesByOffice(office));

        model.addAttribute("sentShipments",
                shipmentService.getShipmentsFromOffice(office));

        model.addAttribute("receivedShipments",
                shipmentService.getShipmentsToOffice(office));

        return "office-details";
    }
}