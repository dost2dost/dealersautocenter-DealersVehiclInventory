package org.dealersautocenter.dealersvehiclinventory.domain.Entity;

import jakarta.persistence.*;
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
    private String name;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private SubscriptionType subscriptionType;
}
