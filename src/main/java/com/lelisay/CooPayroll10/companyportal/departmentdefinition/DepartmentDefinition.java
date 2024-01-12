package com.lelisay.CooPayroll10.companyportal.departmentdefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.departmentdefinition.dto.DepartmentDefinitionRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class DepartmentDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;
    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;
    private boolean isActive = true;


    public static DepartmentDefinition fromDTO(DepartmentDefinitionRequestDTO departmentDefinitionRequestDTO) {
        DepartmentDefinition departmentDefinition = new DepartmentDefinition();
        departmentDefinition.setDepartmentName(departmentDefinitionRequestDTO.getDepartmentName());
        // You may set other fields as needed
        return departmentDefinition;
    }
}
