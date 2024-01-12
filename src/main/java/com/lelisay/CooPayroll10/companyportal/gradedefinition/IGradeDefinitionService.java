package com.lelisay.CooPayroll10.companyportal.gradedefinition;

import java.util.List;

public interface IGradeDefinitionService {
    List<GradeDefinition> getCompanyGrade();

    GradeDefinition saveNewGrade(GradeDefinition newGradeDefinition, Long companyId);
}
