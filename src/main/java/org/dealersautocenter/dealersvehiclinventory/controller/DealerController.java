package org.dealersautocenter.dealersvehiclinventory.controller;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Dealer;
import org.dealersautocenter.dealersvehiclinventory.service.DealerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {
    private final DealerService dealerService;
    @PostMapping
    public Dealer create(@RequestBody Dealer dealer){
        return dealerService.create(dealer);
    }
    @GetMapping("/{id}")
    public Dealer get(@PathVariable UUID id){
        return dealerService.get(id);
    }
    @GetMapping
    public Page<Dealer> list(Pageable pageable){
        return dealerService.getAll(pageable);
    }
    @PatchMapping("/{id}")
    public Dealer update(@PathVariable UUID id, @RequestBody Dealer dealer){
        return dealerService.update(id, dealer);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        dealerService.delete(id);
    }
}
