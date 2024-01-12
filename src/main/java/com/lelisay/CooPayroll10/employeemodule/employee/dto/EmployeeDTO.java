package com.lelisay.CooPayroll10.employeemodule.employee.dto;

import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.address.dto.AddressResponseDTO;

public class EmployeeDTO {
    private String firstName;
    private String lastName;

    public static EmployeeDTO fromEntity(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        if (employee != null) {
            dto.firstName = employee.getFirstName();
            dto.lastName = employee.getLastName();
            // Set other fields as needed
        }
        return dto;
    }
}
