package com.logisticcompany.logistic_company.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Client sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @ManyToOne
    @JoinColumn(name = "registered_by_id")
    private Employee registeredBy;

    @ManyToOne
    @JoinColumn(name = "source_office_id")
    private Office sourceOffice;

    @ManyToOne
    @JoinColumn(name = "destination_office_id")
    private Office destinationOffice;

    private String deliveryAddress;

    private String description;

    private double weight;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private double price;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateDelivered;

    @Column(unique = true)
    private String trackingNumber;

    public Shipment() {}

    public Long getId() {
        return id;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    public Employee getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(Employee registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Office getSourceOffice() {
        return sourceOffice;
    }

    public void setSourceOffice(Office sourceOffice) {
        this.sourceOffice = sourceOffice;
    }

    public Office getDestinationOffice() {
        return destinationOffice;
    }

    public void setDestinationOffice(Office destinationOffice) {
        this.destinationOffice = destinationOffice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }


    public LocalDateTime getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(LocalDateTime dateDelivered) {
        this.dateDelivered = dateDelivered;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}