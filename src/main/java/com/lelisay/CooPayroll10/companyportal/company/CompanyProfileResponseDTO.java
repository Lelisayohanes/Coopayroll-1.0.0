package com.lelisay.CooPayroll10.companyportal.company;

import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGateway;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyProfileResponseDTO {
    private Long id;
    private String companyName;
    private String companyCode;
    private String companyBrandName;
    private String companyEmail;
    private Long numberOfEmployee;
    private String PaymentAccountHolder;
    private String PaymentAccountNumber;

    // other fields...

    // Constructor or builder method to create DTO from CompanyProfile

    public static CompanyProfileResponseDTO fromEntity(CompanyProfile companyProfile) {
        CompanyProfileResponseDTO dto = new CompanyProfileResponseDTO();
        dto.setId(companyProfile.getId());
        dto.setCompanyName(companyProfile.getCompanyName());
        dto.setCompanyCode(companyProfile.getCompanyCode());
        dto.setCompanyBrandName(companyProfile.getCompanyBrandName());
        dto.setCompanyEmail(companyProfile.getActiveContact().getEmail());
        dto.setPaymentAccountHolder(companyProfile.getActivePaymentGateway().getPaymentAccountHolder());
        dto.setPaymentAccountNumber(companyProfile.getActivePaymentGateway().getPaymentAccount());
        dto.setNumberOfEmployee(companyProfile.getNumberOfEmployee());
        // set other fields...
        return dto;
    }
}
