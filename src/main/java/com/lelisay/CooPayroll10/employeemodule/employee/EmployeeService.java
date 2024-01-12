package com.lelisay.CooPayroll10.employeemodule.employee;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfileService;
import com.lelisay.CooPayroll10.coremodule.user.user.UserRecord;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.address.AddressService;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import com.lelisay.CooPayroll10.generalmodule.contact.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{
    private final IEmployeeRepository employeeRepository;
    private final AddressService addressService;
    private final ContactService contactService;

    @Override
    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }


    @Override
    public Employee saveEmployee(Employee newEmployee) {
        try {
            //0. check if duplicated
            Employee employee = newEmployee;

            String exist = checkIfDuplicated(newEmployee, newEmployee.getActiveContact());
            if(exist=="valid"){
                //1. save employee
                Employee savedEmployee = addEmployee(newEmployee);
                //2. save address of employee
                Address addedAddress = addEmployeeAddress(newEmployee, savedEmployee);
                //3. save contact of employee
                Contact addedContact = addEmployeeContact(newEmployee,savedEmployee);
                //4.update employee address and contact
                Employee updateEmployee = updateEmployeeAddressAndContact(savedEmployee,addedAddress,addedContact);
                //5. send email for each employee

                //6. give response
                Employee structuredEmployeeData = structureSavedData(updateEmployee, addedAddress, addedContact);
                return structuredEmployeeData;

            }else {
                log.error(exist);
                return null;
            }

        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("Error in saveCompanyProfile: " + e.getMessage(), e);

            // Handle exceptions and return an appropriate HTTP response
            return null;
        }
    }

    private Employee updateEmployeeAddressAndContact(Employee savedEmployee, Address addedAddress, Contact addedContact) {
        if (savedEmployee != null) {
            // Update the active address
            savedEmployee.setActiveAddress(addedAddress);

            // Update the active contact
            savedEmployee.setActiveContact(addedContact);

            // You may want to update other information as needed

            // Save the updated employee
            return employeeRepository.save(savedEmployee);
        } else {
            // Handle the case where the employee is not found
            return null;
        }
    }

    private Employee structureSavedData(Employee savedEmployee, Address addedAddress, Contact addedContact) {
        Employee returnSavedData = new Employee();
        returnSavedData.setId(savedEmployee.getId());
        returnSavedData.setFirstName(savedEmployee.getFirstName());
        returnSavedData.setLastName(savedEmployee.getLastName());
        returnSavedData.setActiveAddress(addedAddress);
        returnSavedData.setActiveContact(addedContact);
        return returnSavedData;
    }

    private Contact addEmployeeContact(Employee newEmployee, Employee savedEmployee) {
        Contact contactData = new Contact();

        contactData.setEmail(newEmployee.getActiveContact().getEmail());
        contactData.setEmail2(newEmployee.getActiveContact().getEmail2());
        contactData.setPhone(newEmployee.getActiveContact().getPhone());
        contactData.setPhone2(newEmployee.getActiveContact().getPhone2());
        contactData.setFax(newEmployee.getActiveContact().getFax());
        contactData.setEmployee(savedEmployee);

        Contact savedEmployeContact = contactService.saveEmployeeContact(contactData);
        return savedEmployeContact;
    }

    private Employee addEmployee(Employee newEmployee) {
        Employee employeeData = new Employee();

        employeeData.setFirstName(newEmployee.getFirstName());
        employeeData.setLastName(newEmployee.getLastName());

        Employee savedEmployee = employeeRepository.save(employeeData);
        return savedEmployee;
    }

    private Address addEmployeeAddress(Employee newEmployee,Employee savedEmployee) {
        Address addressData = new Address();

        addressData.setEmployee(savedEmployee);
        addressData.setCity(newEmployee.getActiveAddress().getCity());
        addressData.setPostalCode(newEmployee.getActiveAddress().getPostalCode());
        addressData.setStreet(newEmployee.getActiveAddress().getStreet());
        addressData.setCountry(newEmployee.getActiveAddress().getCountry());
        addressData.setState(newEmployee.getActiveAddress().getState());

        Address savedAddress = addressService.addEmployeeAddress(addressData);
        return savedAddress;
    }

    private String checkIfDuplicated(Employee newEmployee, Contact activeContact) {
        //collect data to check

        // Check for null or empty values
        if (isNullOrEmpty(activeContact.getPhone())) {
            return "Phone number  is null or empty";
        }

        if (isNullOrEmpty(activeContact.getEmail())) {
            return "Email address is null or empty";
        }

        return "valid";
    }

    private boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        // Add more checks for other types if needed...
        return false;
    }

}
