package com.lelisay.CooPayroll10.companyportal.gradedefinition.dto;

import com.lelisay.CooPayroll10.companyportal.gradedefinition.GradeDefinition;
import lombok.Data;

@Data
public class GradeDefinitionRequestDTO {
    private String gradeLabel;
    private Float maximumSalary;
    private Float minimumSalary;
    private boolean isActive;

}
