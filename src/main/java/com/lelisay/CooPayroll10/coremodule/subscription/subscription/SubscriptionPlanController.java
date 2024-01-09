package com.lelisay.CooPayroll10.coremodule.subscription.subscription;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author lelisay
 */

@RestController
@RequestMapping("/api/v1/subscription-plan")
@RequiredArgsConstructor
public class SubscriptionPlanController {
    private final SubscriptionPlanService subscriptionPlanService;

    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptions(){
        return ResponseEntity.ok(subscriptionPlanService.getAllSubscriptions());
    }

    @GetMapping("/{id}")
    public Optional<SubscriptionPlan> getById(@PathVariable("id") Long subscriptionId){
        return subscriptionPlanService.findById(subscriptionId);
    }

    @PostMapping("/add")
    public ResponseEntity<SubscriptionPlan> add(@RequestBody SubscriptionPlan subscriptionPlan){
        return new ResponseEntity<>(subscriptionPlanService.add(subscriptionPlan), CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<SubscriptionPlan> update(@RequestBody SubscriptionPlan subscriptionPlan){
        return new ResponseEntity<>(subscriptionPlanService.update(subscriptionPlan), OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long subscriptionId){
        subscriptionPlanService.delete(subscriptionId);
    }

}
