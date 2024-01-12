package com.lelisay.CooPayroll10.companyportal.positiondefinition.dto;


import com.lelisay.CooPayroll10.companyportal.positiondefinition.PositionDefinition;
import lombok.Data;

@Data
public class PositionDefinitionResponseDTO {
    private String positionLabel;

    public static PositionDefinitionResponseDTO fromEntity(PositionDefinition positionDefinition) {
        PositionDefinitionResponseDTO dto = new PositionDefinitionResponseDTO();
        dto.setPositionLabel(positionDefinition.getPositionLabel());
        // set other fields...
        return dto;
    }

}
