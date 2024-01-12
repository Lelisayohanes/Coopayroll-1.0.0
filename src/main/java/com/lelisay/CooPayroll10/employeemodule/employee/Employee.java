package com.lelisay.CooPayroll10.employeemodule.employee;

import com.lelisay.CooPayroll10.employeemodule.employee.dto.EmployeeRequestDTO;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    // Active Address
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_address_id")
    private com.lelisay.CooPayroll10.generalmodule.address.Address activeAddress;

    // Previous Addresses
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> previousAddresses;

    // Active Contact
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_contact_id")
    private Contact activeContact;

    // Previous Contact
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> previousContact;


    public static Employee fromDTO(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setActiveAddress(employeeRequestDTO.getActiveAddress());
        employee.setActiveContact(employeeRequestDTO.getActiveContact());

        // You may set other fields as needed

        return employee;
    }

}
