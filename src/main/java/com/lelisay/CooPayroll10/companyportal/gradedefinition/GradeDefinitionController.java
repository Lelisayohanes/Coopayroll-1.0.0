package com.lelisay.CooPayroll10.companyportal.gradedefinition;


import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionRequestDTO;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionResponseDTO;
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
@RequestMapping("/api/v1/grade_definition")
public class GradeDefinitionController {

    private final GradeDefinitionService gradeDefinitionService;
    @GetMapping
    public ResponseEntity<?> getAllGrade() {
        List<GradeDefinition> gradeDefinitions = gradeDefinitionService.getCompanyGrade();
        log.debug("Request received to view a department for company with : ");
        if (gradeDefinitions == null || gradeDefinitions.isEmpty()) {
            int statusCode = 404;
            String errorMessage = "Grade not defined under this company.";
            String requestInformation = "Request: GET /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<GradeDefinitionResponseDTO> gradeDefinitionResponseDTOList = gradeDefinitions.stream()
                .map(GradeDefinitionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(gradeDefinitionResponseDTOList);
    }

    @PostMapping("/add/{companyId}")
    public ResponseEntity<?> createCompanyGrade(
            @RequestBody GradeDefinitionRequestDTO gradeDefinitionRequestDTO,
            @PathVariable Long companyId) {
        log.debug("Request received to create a new department for company with id: {}", companyId);
        // Validate the incoming DTO, perform any necessary business logic, and then save the employee

        GradeDefinition newGradeDefinition = GradeDefinition.fromDTO(gradeDefinitionRequestDTO);
        GradeDefinition savedGradeDefinition = gradeDefinitionService.saveNewGrade(newGradeDefinition,companyId);

        if (savedGradeDefinition != null) {
            GradeDefinitionResponseDTO responseDTO = GradeDefinitionResponseDTO.fromEntity(savedGradeDefinition);
            CustomSuccessResponse successResponse = new CustomSuccessResponse(HttpStatus.CREATED.value(), "Grade created successfully", responseDTO);
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } else {
            int statusCode = 500; // Internal Server Error
            String errorMessage = "Unable to create grade for this company .";
            String requestInformation = "Request: POST /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
