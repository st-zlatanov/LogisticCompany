package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.model.Office;
import com.logisticcompany.logistic_company.repository.OfficeRepository;
import com.logisticcompany.logistic_company.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final ShipmentRepository shipmentRepository;

    public OfficeService(OfficeRepository officeRepository, ShipmentRepository shipmentRepository) {
        this.officeRepository = officeRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public Office getOfficeById(Long id){
        return officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));
    }

    public void updateOffice(Long id, Office updatedOffice){

        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        office.setCity(updatedOffice.getCity());
        office.setAddress(updatedOffice.getAddress());

        officeRepository.save(office);
    }

    public void deleteOffice(Long id){

        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        if(shipmentRepository.existsBySourceOffice(office)
                || shipmentRepository.existsByDestinationOffice(office)){

            throw new RuntimeException("Office has shipments");
        }
        officeRepository.deleteById(id);
    }
}