package com.lelisay.CooPayroll10.companyportal.gradedefinition.dto;

import com.lelisay.CooPayroll10.companyportal.gradedefinition.GradeDefinition;
import lombok.Data;

@Data
public class GradeDefinitionResponseDTO {

    private String gradeLabel;
    private Float maximumSalary;
    private Float minimumSalary;
    public static GradeDefinitionResponseDTO fromEntity(GradeDefinition gradeDefinition) {
        GradeDefinitionResponseDTO dto = new GradeDefinitionResponseDTO();
        dto.setGradeLabel(gradeDefinition.getGradeLabel());
        dto.setMaximumSalary(gradeDefinition.getMaximumSalary());
        dto.setMinimumSalary(gradeDefinition.getMinimumSalary());
        // set other fields...
        return dto;
    }

}
