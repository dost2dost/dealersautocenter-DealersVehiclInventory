package org.dealersautocenter.dealersvehiclinventory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@MappedSuperclass
public abstract class BaseTenantEntity {
    @Column(name = "tenant_id",nullable = false)
    private String tenantId;
}
