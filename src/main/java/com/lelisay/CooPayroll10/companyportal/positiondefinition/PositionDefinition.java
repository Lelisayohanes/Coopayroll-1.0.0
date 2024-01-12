package com.lelisay.CooPayroll10.companyportal.positiondefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.GradeDefinition;
import com.lelisay.CooPayroll10.companyportal.gradedefinition.dto.GradeDefinitionRequestDTO;
import com.lelisay.CooPayroll10.companyportal.positiondefinition.dto.PositionDefinitionRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class PositionDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String positionLabel;
    private boolean isActive= true;
    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    public static PositionDefinition fromDTO(PositionDefinitionRequestDTO positionDefinitionRequestDTO) {
        PositionDefinition positionDefinition = new PositionDefinition();
        positionDefinition.setPositionLabel(positionDefinitionRequestDTO.getPositionLabel());
        // You may set other fields as needed
        return positionDefinition;
    }


}
