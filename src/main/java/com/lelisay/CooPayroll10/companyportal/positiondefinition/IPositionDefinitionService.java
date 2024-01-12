package com.lelisay.CooPayroll10.companyportal.positiondefinition;

import java.util.List;

public interface IPositionDefinitionService {
    List<PositionDefinition> getCompanyPositions();

    PositionDefinition saveNewPosition(PositionDefinition newPositionDefinition, Long companyId);
}
