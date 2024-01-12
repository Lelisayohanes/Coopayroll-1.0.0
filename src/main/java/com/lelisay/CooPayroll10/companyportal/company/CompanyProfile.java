package com.lelisay.CooPayroll10.companyportal.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lelisay.CooPayroll10.companyportal.departmentdefinition.DepartmentDefinition;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGateway;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
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
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String companyName;
    @Column(unique = true)
    private String companyCode;
    @Column(unique = true)
    private String companyBrandName;
    private Long numberOfEmployee;

    // Active Address
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_address_id")
    private Address activeAddress;

    // Previous Addresses
    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> previousAddresses;

    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_payment_gateway_id")

    private PaymentGateway activePaymentGateway;

    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<PaymentGateway> previousPaymentGateways;

    // Active Contact
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_contact_id")
    private Contact activeContact;

    // Previous Contact
    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> previousContact;

    //department
    @OneToMany(mappedBy = "companyProfile")
    private List<DepartmentDefinition> departmentDefinitions;

}
