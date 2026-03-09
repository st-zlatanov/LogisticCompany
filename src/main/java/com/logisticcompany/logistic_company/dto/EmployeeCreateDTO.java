package com.logisticcompany.logistic_company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Position is required")
    private String position;

    @NotNull(message = "Office is required")
    private Long officeId;

    @NotNull(message = "User is required")
    private Long userId;

    public EmployeeCreateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}