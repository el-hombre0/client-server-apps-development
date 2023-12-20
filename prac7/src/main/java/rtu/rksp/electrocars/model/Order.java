package rtu.rksp.electrocars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("orders")
public class Order {
    @Id
    private Long id;
    @Column("client_name")
    private String clientName;

    @Column("client_phone")
    private String clientPhone;

    @Column("car_model")
    private String carModel;

    @Column("required_kilowatts")
    private double requiredKiloWatts;

    @Column("distance_to_client")
    private double distanceToClient;

    @Column("cost")
    private double cost;
}