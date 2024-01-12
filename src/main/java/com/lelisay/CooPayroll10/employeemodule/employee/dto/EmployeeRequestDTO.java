package com.lelisay.CooPayroll10.employeemodule.employee.dto;

import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    private String firstName;
    private String lastName;
    private Address activeAddress;
    private Contact activeContact;
    // Constructors, getters, setters, and other methods as needed

}
