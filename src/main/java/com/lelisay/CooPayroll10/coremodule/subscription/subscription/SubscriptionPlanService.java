package com.lelisay.CooPayroll10.coremodule.subscription.subscription;

import com.lelisay.CooPayroll10.generalmodule.exception.SubscriptionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author lelisay
 */
@Service
@RequiredArgsConstructor
public class SubscriptionPlanService implements ISubscriptionPlanService {
    private final SubscriptionPlanRepository subscriptionPlanRepository;


    @Override
    public List<SubscriptionPlan> getAllSubscriptions() {
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public SubscriptionPlan add(SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanRepository.save(subscriptionPlan);
    }

    @Override
    public Optional<SubscriptionPlan> findById(Long subscriptionId) {
        return Optional.ofNullable(subscriptionPlanRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("No subscription found with the id : " + subscriptionId)));
    }

    @Override
    public void delete(Long subscriptionId) {
        Optional<SubscriptionPlan> theSubscription = findById(subscriptionId);
        theSubscription.ifPresent(book -> subscriptionPlanRepository.deleteById(book.getId()));
    }

    @Override
    public SubscriptionPlan update(SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanRepository.save(subscriptionPlan);
    }
}
