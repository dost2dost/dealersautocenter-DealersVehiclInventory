package org.dealersautocenter.dealersvehiclinventory.domain.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dealersautocenter.dealersvehiclinventory.domain.BaseTenantEntity;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;

import java.util.UUID;

@Entity
@Setter @Getter
@Table(name = "vehicles")
public class Vehicle extends BaseTenantEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private  UUID dealerId;
    private String model;
    private Double price;
    @Enumerated(value = EnumType.STRING)
    private VehicleStatus status;

}
