package org.dealersautocenter.dealersvehiclinventory.controller;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;
import org.dealersautocenter.dealersvehiclinventory.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    
    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle){
        return vehicleService.create(vehicle);
    }
    
    @GetMapping("/{id}")
    public Vehicle get(@PathVariable UUID id){
        return vehicleService.get(id);
    }
    
    @GetMapping
    public Page<Vehicle> getVehicles(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) String subscription,
            Pageable pageable
    ){
        return vehicleService.getVehicles(model, status, priceMin, priceMax, subscription, pageable);
    }
    
    @PatchMapping("/{id}")
    public Vehicle update(@PathVariable UUID id, @RequestBody Vehicle vehicle){
        return vehicleService.update(id, vehicle);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        vehicleService.delete(id);
    }
}
