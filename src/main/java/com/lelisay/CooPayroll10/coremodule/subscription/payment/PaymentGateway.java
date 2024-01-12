package com.lelisay.CooPayroll10.coremodule.subscription.payment;

import com.lelisay.CooPayroll10.coremodule.subscription.billing.Billing;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class PaymentGateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentGateway;
    @Column(unique = true)
    private String paymentAccount;
    private String paymentAccountHolder;
    private String paymentOTP;
    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    private boolean isActive;

    @OneToMany(mappedBy = "paymentGateway")
    private List<Billing> billings;

    // Getters and setters...
}
