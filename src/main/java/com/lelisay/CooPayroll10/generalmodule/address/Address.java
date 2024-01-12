package com.lelisay.CooPayroll10.generalmodule.address;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyProfile companyProfile;

    // Add meaningful comments here
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
