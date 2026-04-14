package org.dealersautocenter.dealersvehiclinventory.service;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.config.TenantContext;
import org.dealersautocenter.dealersvehiclinventory.config.VehicleSpecification;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Dealer;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.SubscriptionType;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;
import org.dealersautocenter.dealersvehiclinventory.repository.DealerRepository;
import org.dealersautocenter.dealersvehiclinventory.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;
    public Page<Vehicle> getVehicles(
            String model,
            VehicleStatus status,
            BigDecimal prceMin,
            BigDecimal priceMax,
            String subscription,
            Pageable pageable
    ){
      String tenantId= TenantContext.getTenantId();
        Specification<Vehicle> spec=
                VehicleSpecification.filter(model,tenantId,status,prceMin,priceMax);
    if("PREMIUM".equalsIgnoreCase(subscription)){
        List<UUID> premiumDealerIds=dealerRepository.findAll().stream()
                .filter(d->d.getTenantId().equals(tenantId))
                .filter(d->d.getSubscriptionType()== SubscriptionType.PREMIUM)
                .map(Dealer::getId)
                .toList();
        spec=spec.and(((root, query, criteriaBuilder) -> root.get("dealerId").in(premiumDealerIds)));
    }
      return vehicleRepository.findAll(spec,pageable);
    }
}
