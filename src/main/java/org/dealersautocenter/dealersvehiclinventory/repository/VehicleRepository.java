package org.dealersautocenter.dealersvehiclinventory.repository;

import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {
Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId);
boolean existsById(UUID id);
}
