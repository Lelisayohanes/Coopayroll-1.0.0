package com.lelisay.CooPayroll10.coremodule.subscription.invoice;

import com.lelisay.CooPayroll10.coremodule.subscription.billing.Billing;
import com.lelisay.CooPayroll10.coremodule.subscription.subscription.SubscriptionPlan;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlan subscriptionPlan;

    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    private boolean isApproved;
    private int duration;

    @OneToOne
    @JoinColumn(name = "billing_id")
    private Billing billing;
}
