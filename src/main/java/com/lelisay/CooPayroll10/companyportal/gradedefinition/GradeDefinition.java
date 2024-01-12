package com.lelisay.CooPayroll10.companyportal.gradedefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class GradeDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gradeLabel;
    private Float maximumSalary;
    private Float minimumSalary;
    private boolean isActive = true;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    public static GradeDefinition fromDTO(GradeDefinitionRequestDTO gradeDefinitionRequestDTO) {
        GradeDefinition gradeDefinition = new GradeDefinition();
        gradeDefinition.setGradeLabel(gradeDefinitionRequestDTO.getGradeLabel());
        gradeDefinition.setMinimumSalary(gradeDefinitionRequestDTO.getMinimumSalary());
        gradeDefinition.setMaximumSalary(gradeDefinitionRequestDTO.getMaximumSalary());
        // You may set other fields as needed
        return gradeDefinition;
    }
}
