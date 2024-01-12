package com.lelisay.CooPayroll10.coremodule.subscription.subscription;

import java.util.List;
import java.util.Optional;
/**
 * @author lelisay
 */
public interface ISubscriptionPlanService {
    List<SubscriptionPlan> getAllSubscriptions();
    SubscriptionPlan add(SubscriptionPlan subscriptionPlan);
    Optional<SubscriptionPlan> findById(Long subscriptionId);
    void delete(Long subscriptionId);
    SubscriptionPlan update(SubscriptionPlan subscriptionPlan);
}
