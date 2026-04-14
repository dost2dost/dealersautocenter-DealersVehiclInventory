package org.dealersautocenter.dealersvehiclinventory.domain.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.dealersautocenter.dealersvehiclinventory.domain.BaseTenantEntity;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter @Getter
@Table(name = "vehicles")
public class Vehicle extends BaseTenantEntity {
    @Id
    @GeneratedValue
    private UUID id;
    
    @NotNull(message = "Dealer ID is required")
    private UUID dealerId;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private BigDecimal price;
    
    @NotNull(message = "Status is required")
    @Enumerated(value = EnumType.STRING)
    private VehicleStatus status;
}
