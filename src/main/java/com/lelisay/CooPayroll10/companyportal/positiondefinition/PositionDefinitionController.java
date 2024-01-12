package com.lelisay.CooPayroll10.companyportal.positiondefinition;

import com.lelisay.CooPayroll10.companyportal.gradedefinition.GradeDefinition;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionRequestDTO;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionResponseDTO;
import com.lelisay.CooPayroll10.companyportal.positiondefinition.dto.PositionDefinitionRequestDTO;
import com.lelisay.CooPayroll10.companyportal.positiondefinition.dto.PositionDefinitionResponseDTO;
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
@RequestMapping("/api/v1/position_definition")
public class PositionDefinitionController {

    private final PositionDefinitionService positionDefinitionService;

    @GetMapping
    public ResponseEntity<?> getAllGrade() {
        List<PositionDefinition> positionDefinitions = positionDefinitionService.getCompanyPositions();
        log.debug("Request received to view a department for company with : ");
        if (positionDefinitions == null || positionDefinitions.isEmpty()) {
            int statusCode = 404;
            String errorMessage = "positions not defined under this company.";
            String requestInformation = "Request: GET /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<PositionDefinitionResponseDTO> positionDefinitionResponseDTOList = positionDefinitions.stream()
                .map(PositionDefinitionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(positionDefinitionResponseDTOList);
    }

    @PostMapping("/add/{companyId}")
    public ResponseEntity<?> createCompanyPosition(
            @RequestBody PositionDefinitionRequestDTO positionDefinitionRequestDTO,
            @PathVariable Long companyId) {
        log.debug("Request received to create a new position for company with id: {}", companyId);
        // Validate the incoming DTO, perform any necessary business logic, and then save the employee

        PositionDefinition newPositionDefinition = PositionDefinition.fromDTO(positionDefinitionRequestDTO);
        PositionDefinition savedPositionDefinition = positionDefinitionService.saveNewPosition(newPositionDefinition, companyId);

        if (savedPositionDefinition != null) {
            PositionDefinitionResponseDTO responseDTO = PositionDefinitionResponseDTO.fromEntity(savedPositionDefinition);
            CustomSuccessResponse successResponse = new CustomSuccessResponse(HttpStatus.CREATED.value(), "Position created successfully", responseDTO);
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } else {
            int statusCode = 500; // Internal Server Error
            String errorMessage = "Unable to create position for this company .";
            String requestInformation = "Request: POST /api/entities/";
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
