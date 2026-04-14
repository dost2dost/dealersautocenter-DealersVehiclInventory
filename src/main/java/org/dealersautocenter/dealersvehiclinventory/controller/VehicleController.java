package org.dealersautocenter.dealersvehiclinventory.controller;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;
import org.dealersautocenter.dealersvehiclinventory.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private  final VehicleService vehicleService;
    @GetMapping
    public Page<Vehicle> getVehicles(
            @RequestHeader(required = false) String model,
            @RequestParam(required = false)VehicleStatus status,
            @RequestParam(required = false)BigDecimal priceMin,
            @RequestParam(required = false)BigDecimal priceMax,
            @RequestParam(required = false)String subscription,
            Pageable pageable
    ){
        return vehicleService.getVehicles(model, status, priceMin, priceMax, subscription, pageable);
    }
}
