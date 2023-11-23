package com.example.prac5_client.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private Long id;
    private String clientName;
    private String clientPhone;
    private String carModel;
    private double requiredKiloWatts;
    private double distanceToClient;
    private double cost;
}
