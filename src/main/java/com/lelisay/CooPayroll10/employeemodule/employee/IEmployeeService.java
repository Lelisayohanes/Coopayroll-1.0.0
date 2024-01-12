package com.lelisay.CooPayroll10.employeemodule.employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getEmployee();

    Employee saveEmployee(Employee newEmployee);
}
