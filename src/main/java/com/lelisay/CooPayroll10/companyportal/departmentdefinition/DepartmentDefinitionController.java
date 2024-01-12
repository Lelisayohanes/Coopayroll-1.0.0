package com.lelisay.CooPayroll10.companyportal.departmentdefinition;

import com.lelisay.CooPayroll10.companyportal.departmentdefinition.dto.DepartmentDefinitionRequestDTO;
import com.lelisay.CooPayroll10.companyportal.departmentdefinition.dto.DepartmentDefinitionResponseDTO;
import com.lelisay.CooPayroll10.employeemodule.employee.Employee;
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
@RequestMapping("/api/v1/department_definition")
public class DepartmentDefinitionController {
    private final DepartmentDefinitionService departmentDefinitionService;

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        List<DepartmentDefinition> departmentDefinitions = departmentDefinitionService.getCompanyDepartment();
        log.debug("Request received to create a new department for company with id: ");
        if (departmentDefinitions == null || departmentDefinitions.isEmpty()) {
            int statusCode = 404;
            String errorMessage = "Department not defined under this company.";
            String requestInformation = "Request: GET /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<DepartmentDefinitionResponseDTO> departmentDefinitionResponseDTOList = departmentDefinitions.stream()
                .map(DepartmentDefinitionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(departmentDefinitionResponseDTOList);
    }


    @PostMapping("/add/{companyId}")
    public ResponseEntity<?> createCompanyDepartment(
            @RequestBody DepartmentDefinitionRequestDTO departmentDefinitionRequestDTO,
            @PathVariable Long companyId) {
        log.debug("Request received to create a new department for company with id: {}", companyId);
        // Validate the incoming DTO, perform any necessary business logic, and then save the employee

        DepartmentDefinition newDepartmentDefinition = DepartmentDefinition.fromDTO(departmentDefinitionRequestDTO);
        DepartmentDefinition savedDepartmentDefinition = departmentDefinitionService.saveNewDepartment(newDepartmentDefinition,companyId);

        if (savedDepartmentDefinition != null) {
            DepartmentDefinitionResponseDTO responseDTO = DepartmentDefinitionResponseDTO.fromEntity(savedDepartmentDefinition);
            CustomSuccessResponse successResponse = new CustomSuccessResponse(HttpStatus.CREATED.value(), "Department created successfully", responseDTO);
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } else {
            int statusCode = 500; // Internal Server Error
            String errorMessage = "Unable to create department for this company .";
            String requestInformation = "Request: POST /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
