package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.dto.ShipmentCreateDTO;
import com.logisticcompany.logistic_company.dto.ShipmentEditDTO;
import com.logisticcompany.logistic_company.model.*;
import com.logisticcompany.logistic_company.repository.ClientRepository;
import com.logisticcompany.logistic_company.repository.EmployeeRepository;
import com.logisticcompany.logistic_company.repository.OfficeRepository;
import com.logisticcompany.logistic_company.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, ClientRepository clientRepository, EmployeeRepository employeeRepository, OfficeRepository officeRepository) {
        this.shipmentRepository = shipmentRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.officeRepository = officeRepository;
    }

    public Shipment createShipment(ShipmentCreateDTO dto) {

        Client sender = clientRepository.findById(dto.getSenderId()).orElseThrow();
        Client receiver = clientRepository.findById(dto.getReceiverId()).orElseThrow();
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();

        Office sourceOffice = officeRepository.findById(dto.getSourceOfficeId()).orElseThrow();

        Shipment shipment = new Shipment();

        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setRegisteredBy(employee);
        shipment.setSourceOffice(sourceOffice);

        shipment.setWeight(dto.getWeight());
        shipment.setDescription(dto.getDescription());
        shipment.setDeliveryType(dto.getDeliveryType());
        shipment.setTrackingNumber(UUID.randomUUID().toString().substring(0,8).toUpperCase());

        if(dto.getDeliveryType() == DeliveryType.OFFICE){
            Office destinationOffice = officeRepository
                    .findById(dto.getDestinationOfficeId())
                    .orElseThrow();

            shipment.setDestinationOffice(destinationOffice);
        }else{
            shipment.setDeliveryAddress(receiver.getAddress());
        }

        double price = dto.getWeight() * 2;

        if(dto.getDeliveryType() == DeliveryType.ADDRESS){
            price += 5;
        }

        shipment.setPrice(price);

        shipment.setStatus(ShipmentStatus.SENT);

        shipment.setDateCreated(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }
    public List<Shipment> getFilteredShipments(String search, ShipmentStatus status){

        if(search != null && search.isBlank()){
            search = null;
        }

        return shipmentRepository.filterShipments(search, status);
    }

    public List<Shipment> getUndeliveredShipments() {
        return shipmentRepository.findByStatus(ShipmentStatus.SENT);
    }

    public List<Shipment> getShipmentsByClient(Long clientId) {
        return shipmentRepository.findBySenderId(clientId);
    }

    public List<Shipment> getShipmentsByEmployee(Long employeeId) {
        return shipmentRepository.findByRegisteredById(employeeId);
    }

    public List<Shipment> getDeliveredShipments() {
        return shipmentRepository.findByStatus(ShipmentStatus.DELIVERED);
    }

    public void deliverShipment(Long id) {

        Shipment shipment = shipmentRepository.findById(id).orElseThrow();

        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDateDelivered(LocalDateTime.now());

        shipmentRepository.save(shipment);
    }

    public List<Shipment> getShipmentsForLoggedUser(String username) {
        return shipmentRepository.findBySender_User_Username(username);
    }

    public Double getRevenue(LocalDateTime start,
                             LocalDateTime end) {

        Double result = shipmentRepository
                .calculateRevenue(start, end);

        return result == null ? 0 : result;
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id).orElseThrow();
    }

    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public List<Shipment> getShipmentsSentByClient(Client client){
        return shipmentRepository.findBySender(client);
    }

    public List<Shipment> getShipmentsReceivedByClient(Client client){
        return shipmentRepository.findByReceiver(client);
    }

    public List<Shipment> getShipmentsFromOffice(Office office){
        return shipmentRepository.findBySourceOffice(office);
    }

    public List<Shipment> getShipmentsToOffice(Office office){
        return shipmentRepository.findByDestinationOffice(office);
    }

    public void updateShipment(Long id, ShipmentEditDTO dto){

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        if(shipment.getStatus() == ShipmentStatus.DELIVERED){
            throw new RuntimeException("Delivered shipments cannot be edited");
        }

        shipment.setSender(clientRepository.findById(dto.getSenderId()).orElseThrow());
        shipment.setReceiver(clientRepository.findById(dto.getReceiverId()).orElseThrow());
        shipment.setSourceOffice(officeRepository.findById(dto.getSourceOfficeId()).orElseThrow());

        if(dto.getDeliveryType() == DeliveryType.OFFICE){

            shipment.setDestinationOffice(
                    officeRepository.findById(dto.getDestinationOfficeId()).orElseThrow()
            );

            shipment.setDeliveryAddress(null);
        }

        if(dto.getDeliveryType() == DeliveryType.ADDRESS){

            shipment.setDestinationOffice(null);
            shipment.setDeliveryAddress(dto.getDeliveryAddress());
        }

        shipment.setDescription(dto.getDescription());
        shipment.setWeight(dto.getWeight());
        shipment.setDeliveryType(dto.getDeliveryType());

        shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id){

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        if(shipment.getStatus() == ShipmentStatus.DELIVERED){
            throw new RuntimeException("Delivered shipments cannot be deleted");
        }

        shipmentRepository.delete(shipment);
    }

}