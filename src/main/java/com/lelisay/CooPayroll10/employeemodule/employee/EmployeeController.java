package com.lelisay.CooPayroll10.employeemodule.employee;


import com.lelisay.CooPayroll10.employeemodule.employee.dto.EmployeeRequestDTO;
import com.lelisay.CooPayroll10.employeemodule.employee.dto.EmployeeResponseDTO;
import com.lelisay.CooPayroll10.generalmodule.response.CustomErrorResponse;
import com.lelisay.CooPayroll10.generalmodule.response.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getEmployee();

        if (employees == null || employees.isEmpty()) {
            int statusCode = 404;
            String errorMessage = "Employees not registered.";
            String requestInformation = "Request: GET /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<EmployeeResponseDTO> employeeResponseDTOList = employees.stream()
                .map(EmployeeResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeResponseDTOList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        // Validate the incoming DTO, perform any necessary business logic, and then save the employee
        Employee newEmployee = Employee.fromDTO(employeeRequestDTO);
        Employee savedEmployee = employeeService.saveEmployee(newEmployee);

        if (savedEmployee != null) {

            EmployeeResponseDTO responseDTO = EmployeeResponseDTO.fromEntity(savedEmployee);
            CustomSuccessResponse successResponse = new CustomSuccessResponse(HttpStatus.CREATED.value(), "Employee created successfully", responseDTO);
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

        } else {

            int statusCode = 500; // Internal Server Error
            String errorMessage = "Unable to create employee record.";
            String requestInformation = "Request: POST /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }




}
