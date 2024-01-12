package com.lelisay.CooPayroll10.companyportal.departmentdefinition;

import java.util.List;

public interface IDepartmentDefinitionService {
    List<DepartmentDefinition> getCompanyDepartment();


    DepartmentDefinition saveNewDepartment(DepartmentDefinition newDepartmentDefinition, Long companyId);
}
