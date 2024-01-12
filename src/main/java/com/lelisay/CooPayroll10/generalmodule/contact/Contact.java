package com.lelisay.CooPayroll10.generalmodule.contact;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String phone;
    private String phone2;
    @Column(unique = true)
    private String email;
    private String email2;
    private String fax;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyProfile companyProfile;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}