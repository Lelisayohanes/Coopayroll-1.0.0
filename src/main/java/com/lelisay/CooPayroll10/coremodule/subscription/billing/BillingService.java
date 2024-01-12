package com.lelisay.CooPayroll10.coremodule.subscription.billing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService implements IBillingService{
    private final IBillingRepository billingRepository;

    @Override
    public Billing payBilling(Billing billing) {
        return billingRepository.save(billing);
    }
}
