package com.lelisay.CooPayroll10.employeemodule.employee.dto;

import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
import com.lelisay.CooPayroll10.generalmodule.address.dto.AddressResponseDTO;
import com.lelisay.CooPayroll10.generalmodule.contact.dto.ContactResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {
    private String firstName;
    private String lastName;
    private AddressResponseDTO activeAddress;
    private ContactResponseDTO activeContact;

    public static EmployeeResponseDTO fromEntity(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setActiveAddress(AddressResponseDTO.fromEntity(employee.getActiveAddress()));
        dto.setActiveContact(ContactResponseDTO.fromEntity(employee.getActiveContact()));
        // set other fields...
        return dto;
    }
}
