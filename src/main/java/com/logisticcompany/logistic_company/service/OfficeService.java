package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.model.Office;
import com.logisticcompany.logistic_company.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }
}