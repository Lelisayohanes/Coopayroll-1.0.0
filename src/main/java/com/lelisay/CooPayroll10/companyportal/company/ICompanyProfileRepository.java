package com.lelisay.CooPayroll10.companyportal.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyProfileRepository extends JpaRepository<CompanyProfile,Long> {
    CompanyProfile findByCompanyCode(String companyCode);
    CompanyProfile findByCompanyName(String companyName);
    CompanyProfile findByCompanyBrandName(String companyBrandName);
    //    implement optional method to fetch or make any other repository related thing

}
