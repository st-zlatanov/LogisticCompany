package com.logisticcompany.logistic_company.dto;

import com.logisticcompany.logistic_company.model.DeliveryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ShipmentCreateDTO {

    @NotNull(message = "Sender is required")
    private Long senderId;

    @NotNull(message = "Receiver is required")
    private Long receiverId;

    private Long employeeId;

    @NotNull(message = "Source office is required")
    private Long sourceOfficeId;
    @NotNull(message = "Office is required")
    private Long destinationOfficeId;

    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than 0")
    private Double weight;
    @NotNull(message = "Delivery type must be selected")
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