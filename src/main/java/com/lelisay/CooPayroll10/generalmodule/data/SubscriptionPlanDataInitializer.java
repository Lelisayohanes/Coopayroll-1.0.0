package com.lelisay.CooPayroll10.generalmodule.data;

import com.lelisay.CooPayroll10.coremodule.subscription.subscription.SubscriptionPlan;
import com.lelisay.CooPayroll10.coremodule.subscription.subscription.SubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanDataInitializer implements CommandLineRunner {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data exists
        if (subscriptionPlanRepository.count() == 0) {
            // Data doesn't exist, initialize it
            SubscriptionPlan defaultSubscriptionPlan = new SubscriptionPlan();
            defaultSubscriptionPlan.setPlanName("Basic Plan");
            defaultSubscriptionPlan.setPlanDescription("Default Plan Description");
            defaultSubscriptionPlan.setPaymentFrequency("Monthly");
            defaultSubscriptionPlan.setPrice(19.99f);

            // Save to the database
            subscriptionPlanRepository.save(defaultSubscriptionPlan);

            // You can add more data initialization as needed
        }
    }
}
