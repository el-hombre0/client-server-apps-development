package com.rksp.prac4.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String clientName;
    private String clientPhone;
    private String carModel;
    private double requiredKiloWatts;
    private double distanceToClient;
    private double cost;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getRequiredKiloWatts() {
        return requiredKiloWatts;
    }

    public void setRequiredKiloWatts(double requiredKiloWatts) {
        this.requiredKiloWatts = requiredKiloWatts;
    }

    public double getDistanceToClient() {
        return distanceToClient;
    }

    public void setDistanceToClient(double distanceToClient) {
        this.distanceToClient = distanceToClient;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
