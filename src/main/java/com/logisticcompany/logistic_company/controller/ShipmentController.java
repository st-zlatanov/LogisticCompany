package com.logisticcompany.logistic_company.controller;

import com.logisticcompany.logistic_company.dto.ShipmentCreateDTO;
import com.logisticcompany.logistic_company.dto.ShipmentEditDTO;
import com.logisticcompany.logistic_company.model.*;
import com.logisticcompany.logistic_company.repository.EmployeeRepository;
import com.logisticcompany.logistic_company.repository.UserRepository;
import com.logisticcompany.logistic_company.security.UserDetailsImpl;
import com.logisticcompany.logistic_company.service.ClientService;
import com.logisticcompany.logistic_company.service.EmployeeService;
import com.logisticcompany.logistic_company.service.OfficeService;
import com.logisticcompany.logistic_company.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final OfficeService officeService;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;


    public ShipmentController(ShipmentService shipmentService, ClientService clientService, EmployeeService employeeService, OfficeService officeService, UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.shipmentService = shipmentService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.officeService = officeService;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }


    @GetMapping
    public String shipments(@RequestParam(required = false) String search,
                            @RequestParam(required = false) ShipmentStatus status,
                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                            Model model){

        User user = userDetails.getUser();

        model.addAttribute("shipments",
                shipmentService.getFilteredShipments(search, status, user));

        return "shipments";
    }

    @GetMapping("/{id}")
    public String shipmentDetails(@PathVariable Long id, Model model, Authentication authentication) {

        Shipment shipment = shipmentService.getShipmentById(id);

        Employee employee = employeeRepository
                .findByUserUsername(authentication.getName())
                .orElse(null);

        model.addAttribute("shipment", shipment);
        model.addAttribute("currentEmployee", employee);


        return "shipment-details";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("shipmentCreateDTO", new ShipmentCreateDTO());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "create-shipment";
    }

    @PostMapping("/create")
    public String createShipment(@Valid @ModelAttribute ShipmentCreateDTO dto,
                                 BindingResult result,
                                 Model model,
                                 Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("offices", officeService.getAllOffices());
            return "create-shipment";
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        dto.setEmployeeId(employee.getId());

        shipmentService.createShipment(dto);

        return "redirect:/shipments";
    }

@PostMapping("/deliver/{id}")
@PreAuthorize("hasRole('EMPLOYEE')")
public String deliverShipment(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){

    shipmentService.markAsDelivered(id, userDetails.getUser());

    return "redirect:/shipments/" + id;
}

    @GetMapping("/client/{id}")
    public String shipmentsByClient(@PathVariable Long id, Model model) {
        model.addAttribute("shipments", shipmentService.getShipmentsByClient(id));
        return "shipments";
    }

    @GetMapping("/employee/{id}")
    public String shipmentsByEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("shipments", shipmentService.getShipmentsByEmployee(id));
        return "shipments";
    }

    @GetMapping("/delivered")
    public String deliveredShipments(Model model) {
        model.addAttribute("shipments", shipmentService.getDeliveredShipments());
        return "shipments";
    }
    @GetMapping("/undelivered")
    public String undelivered(Model model) {
        model.addAttribute("shipments",
                shipmentService.getUndeliveredShipments());
        return "shipments";
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public String revenue(@RequestParam(required = false) LocalDate start,
                          @RequestParam(required = false) LocalDate end,
                          Model model){

        if(start != null && end != null){

            Double revenue = shipmentService.getRevenue(
                    start.atStartOfDay(),
                    end.atTime(23,59)
            );

            model.addAttribute("revenue", revenue);
        }

        return "revenue";
    }


    @GetMapping("/my")
    public String myShipments(Authentication authentication, Model model) {

        String username = authentication.getName();

        model.addAttribute("shipments",
                shipmentService.getShipmentsForLoggedUser(username));

        return "shipments";
    }

    @GetMapping("/track/{trackingNumber}")
    public String trackShipment(@PathVariable String trackingNumber, Model model) {

        Shipment shipment = shipmentService.getShipmentByTrackingNumber(trackingNumber);

        model.addAttribute("shipment", shipment);

        return "track-shipment";
    }

    @GetMapping("/edit/{id}")
    public String editShipment(@PathVariable Long id, Model model){

        Shipment shipment = shipmentService.getShipmentById(id);

        ShipmentEditDTO dto = new ShipmentEditDTO();

        dto.setSenderId(shipment.getSender().getId());
        dto.setReceiverId(shipment.getReceiver().getId());
        dto.setSourceOfficeId(shipment.getSourceOffice().getId());

        if(shipment.getDestinationOffice() != null){
            dto.setDestinationOfficeId(shipment.getDestinationOffice().getId());
        }

        dto.setDeliveryAddress(shipment.getDeliveryAddress());
        dto.setDescription(shipment.getDescription());
        dto.setWeight(shipment.getWeight());
        dto.setDeliveryType(shipment.getDeliveryType());

        model.addAttribute("shipment", dto);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("offices", officeService.getAllOffices());

        return "edit-shipment";
    }

    @PostMapping("/edit/{id}")
    public String updateShipment(@PathVariable Long id,
                                 @ModelAttribute ShipmentEditDTO dto){

        shipmentService.updateShipment(id, dto);

        return "redirect:/shipments";
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/delete/{id}")
    public String deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipments";
    }

}