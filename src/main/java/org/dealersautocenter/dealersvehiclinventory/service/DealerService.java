package org.dealersautocenter.dealersvehiclinventory.service;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.config.TenantContext;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Dealer;
import org.dealersautocenter.dealersvehiclinventory.exception.CrossTenantAccessException;
import org.dealersautocenter.dealersvehiclinventory.repository.DealerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealerService {
    private final DealerRepository dealerRepository;
    
    public Dealer create(Dealer dealer){
        dealer.setTenantId(TenantContext.getTenantId());
        return dealerRepository.save(dealer);
    }
    
    public Dealer get(UUID id){
        String tenantId = TenantContext.getTenantId();
        if(dealerRepository.existsById(id)){
            return dealerRepository.findByIdAndTenantId(id, tenantId)
                    .orElseThrow(() -> new CrossTenantAccessException("Access to dealer " + id + " is forbidden. It belongs to a different tenant."));
        }
        throw new RuntimeException("Dealer with id " + id + " not found");
    }
    
    public Page<Dealer> getAll(Pageable pageable){
        return dealerRepository.findByTenantId(TenantContext.getTenantId(), pageable);
    }
    
    public Dealer update(UUID id, Dealer updated){
        Dealer d = get(id);
        d.setName(updated.getName());
        d.setSubscriptionType(updated.getSubscriptionType());
        d.setEmail(updated.getEmail());
        return dealerRepository.save(d);
    }
    
    public void delete(UUID id){
        Dealer d = get(id);
        dealerRepository.delete(d);
    }
    
    public List<Object[]> countBySubscription(){
        return dealerRepository.countBySubscription(TenantContext.getTenantId());
    }
}
