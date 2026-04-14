package org.dealersautocenter.dealersvehiclinventory.controller;

import lombok.RequiredArgsConstructor;
import org.dealersautocenter.dealersvehiclinventory.repository.DealerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final DealerRepository dealerRepository;

    @PreAuthorize("hasRole('GLOBAL_ADMIN')")
    @GetMapping("/dealers/countBySubscription")
    public Map<String,Long> count(){
        Map<String,Long> result = new HashMap<>();
        for(Object[]row:dealerRepository.countBySubscription()){
            result.put(row[0].toString(),(Long) row[1]);
        }

        return  result;
    }

}
