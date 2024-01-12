package com.lelisay.CooPayroll10.companyportal.departmentdefinition.dto;

import com.lelisay.CooPayroll10.companyportal.departmentdefinition.DepartmentDefinition;
import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
import com.lelisay.CooPayroll10.employeemodule.employee.dto.EmployeeResponseDTO;
import com.lelisay.CooPayroll10.generalmodule.address.dto.AddressResponseDTO;
import com.lelisay.CooPayroll10.generalmodule.contact.dto.ContactResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDefinitionResponseDTO {

    private String departmentName;

    public static DepartmentDefinitionResponseDTO fromEntity(DepartmentDefinition departmentDefinition) {
        DepartmentDefinitionResponseDTO dto = new DepartmentDefinitionResponseDTO();
        dto.setDepartmentName(departmentDefinition.getDepartmentName());
        // set other fields...
        return dto;
    }
}
