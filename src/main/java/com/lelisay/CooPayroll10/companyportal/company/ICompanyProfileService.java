package com.lelisay.CooPayroll10.companyportal.company;

import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.companyportal.request.CompanyProfileRequest;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompanyProfileService {
    /**
     * what is done by company as client
     */
    //add company info
//    CompanyProfile saveCompanyProfile(CompanyProfile companyProfile);

    ResponseEntity<CompanyProfileRequest> saveCompanyProfile(CompanyProfileRequest companyProfile);

    CompanyProfile updateCompanyProfile(CompanyProfile savedCompany, Address savedAddress, Contact savedContact);

    //get company profile with Identification
    CompanyProfile getCompanyProfileById(Long companyId);
    //update company address
    CompanyProfile updateAddressOfCompanyProfile(Long companyId, CompanyProfile updatedCompanyProfile);
    //update company contact
    CompanyProfile updateContactOfCompanyProfile(Long companyId, CompanyProfile updatedCompanyProfile);
    //update company profile

    /**
     *
     * what is done by super admin
     */
    //if irrelevant info
    void deleteCompanyProfile(Long companyId);

    //find by name of company
    CompanyProfile findByCompanyName(String companyName);
    CompanyProfile findByCompanyBrandName(String companyBrandName);
    //find number of employee
    Long countEmployees(Long companyId);
    //list all registered company
    List<CompanyProfile> getAllCompanyProfiles();
    //fetch company with more than n
    List<CompanyProfile> findCompaniesWithMoreEmployeesThan(int employeeCount);
    /**
     *
     * what is done by all autheticated user
     */
    //
    CompanyProfile findByCompanyCode(String companyCode);

    CompanyProfile getCompanyProfileByCompanyCode(String companyCode);
}
