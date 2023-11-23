package com.rksp.prac5.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "order")
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
}