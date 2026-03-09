package com.logisticcompany.logistic_company.dto;

import com.logisticcompany.logistic_company.model.DeliveryType;
import jakarta.validation.constraints.NotNull;

public class ShipmentCreateDTO {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    private Long employeeId;

    @NotNull
    private Long sourceOfficeId;

    private Long destinationOfficeId;

    private String description;

    private Double weight;

    @NotNull
    private DeliveryType deliveryType;

    public ShipmentCreateDTO() {}

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getSourceOfficeId() { return sourceOfficeId; }
    public void setSourceOfficeId(Long sourceOfficeId) { this.sourceOfficeId = sourceOfficeId; }

    public Long getDestinationOfficeId() { return destinationOfficeId; }
    public void setDestinationOfficeId(Long destinationOfficeId) { this.destinationOfficeId = destinationOfficeId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public DeliveryType getDeliveryType() { return deliveryType; }
    public void setDeliveryType(DeliveryType deliveryType) { this.deliveryType = deliveryType; }
}