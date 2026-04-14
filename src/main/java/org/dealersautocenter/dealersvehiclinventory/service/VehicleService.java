package org.dealersautocenter.dealersvehiclinventory.service;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.config.TenantContext;
import org.dealersautocenter.dealersvehiclinventory.config.VehicleSpecification;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Dealer;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.SubscriptionType;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;
import org.dealersautocenter.dealersvehiclinventory.exception.CrossTenantAccessException;
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
    
    public Vehicle create(Vehicle vehicle){
        vehicle.setTenantId(TenantContext.getTenantId());
        return vehicleRepository.save(vehicle);
    }
    
    public Vehicle get(UUID id){
        String tenantId = TenantContext.getTenantId();
        if(vehicleRepository.existsById(id)){
            return vehicleRepository.findByIdAndTenantId(id, tenantId)
                    .orElseThrow(()->new CrossTenantAccessException("Access to vehicle " + id + " is forbidden. It belongs to a different tenant."));
        }
        throw new RuntimeException("Vehicle with id " + id + " not found");
    }
    
    public Page<Vehicle> getVehicles(
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax,
            String subscription,
            Pageable pageable
    ){
        String tenantId = TenantContext.getTenantId();
        Specification<Vehicle> spec = VehicleSpecification.filter(model, tenantId, status, priceMin, priceMax);
        
        if(subscription != null && !subscription.isEmpty()){
            try {
                SubscriptionType subscriptionType = SubscriptionType.valueOf(subscription.toUpperCase());
                List<UUID> dealerIds = dealerRepository.findAll().stream()
                        .filter(d -> d.getTenantId().equals(tenantId))
                        .filter(d -> d.getSubscriptionType() == subscriptionType)
                        .map(Dealer::getId)
                        .toList();
                spec = spec.and((root, query, criteriaBuilder) -> root.get("dealerId").in(dealerIds));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid subscription type. Must be BASIC or PREMIUM");
            }
        }
        return vehicleRepository.findAll(spec, pageable);
    }
    
    public Vehicle update(UUID id, Vehicle updated){
        Vehicle vehicle = get(id);
        vehicle.setModel(updated.getModel());
        vehicle.setPrice(updated.getPrice());
        vehicle.setStatus(updated.getStatus());
        vehicle.setDealerId(updated.getDealerId());
        return vehicleRepository.save(vehicle);
    }
    
    public void delete(UUID id){
        Vehicle vehicle = get(id);
        vehicleRepository.delete(vehicle);
    }
}
