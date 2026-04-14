package org.dealersautocenter.dealersvehiclinventory.config;

import jakarta.persistence.criteria.Predicate;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Vehicle;
import org.dealersautocenter.dealersvehiclinventory.domain.Enums.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {
    public static Specification<Vehicle>filter(
    String model,
    String tenantId,
    VehicleStatus status,
    BigDecimal priceMin,
    BigDecimal priceMax
    ){
        return(root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("tenantId"),tenantId));
            if(model!=null)
                predicates.add(cb.like(root.get("model"),"%"+model+"%"));
            if(status!=null)
                predicates.add(cb.equal(root.get("status"),status));
            if(priceMin!=null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"),priceMin));
            if(priceMax!=null)
                predicates.add(cb.lessThanOrEqualTo(root.get("price"),priceMax));

        return cb.and(predicates.toArray(new Predicate[0]));
        };

    }
}
