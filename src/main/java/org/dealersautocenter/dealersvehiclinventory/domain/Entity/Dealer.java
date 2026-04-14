package org.dealersautocenter.dealersvehiclinventory.domain.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.dealersautocenter.dealersvehiclinventory.domain.BaseTenantEntity;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.SubscriptionType;

import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "dealers")
public class Dealer extends BaseTenantEntity {
    @Id
    @GeneratedValue
    private UUID id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    
    @NotNull(message = "Subscription type is required")
    @Enumerated(value = EnumType.STRING)
    private SubscriptionType subscriptionType;
}
