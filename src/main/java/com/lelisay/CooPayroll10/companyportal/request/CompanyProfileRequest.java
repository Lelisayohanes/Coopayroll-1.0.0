package com.lelisay.CooPayroll10.companyportal.request;

import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGateway;
import com.lelisay.CooPayroll10.coremodule.subscription.subscription.SubscriptionPlan;
import lombok.Data;

@Data
public class CompanyProfileRequest {
    private com.lelisay.CooPayroll10.companyportal.company.CompanyProfile CompanyProfile;
    private  SubscriptionPlan subscriptionPlan;
    private  PaymentGateway paymentGateway;
    private int duration;

    // Getters and setters...
}