package com.lelisay.CooPayroll10.companyportal.companysubscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySubscriptionService implements ICompanySubscriptionService {
    private final ICompanySubscriptionRepository companySubscriptionRepository;
    @Override
    public CompanySubscription saveCompanySubscription(CompanySubscription companySubscription) {
        return companySubscriptionRepository.save(companySubscription);
    }


}
