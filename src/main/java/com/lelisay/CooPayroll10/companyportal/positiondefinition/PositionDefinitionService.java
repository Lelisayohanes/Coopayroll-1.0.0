package com.lelisay.CooPayroll10.companyportal.positiondefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionDefinitionService implements IPositionDefinitionService {
    private final IPositionDefinitionRepository positionDefinitionRepository;
    private final CompanyProfileService companyProfileService;

    @Override
    public List<PositionDefinition> getCompanyPositions() {
        return positionDefinitionRepository.findAll();
    }

    @Override
    public PositionDefinition saveNewPosition(PositionDefinition newPositionDefinition, Long companyId) {
        CompanyProfile setCompany = companyProfileService.getCompanyProfileById(companyId);
        newPositionDefinition.setCompanyProfile(setCompany);

        return positionDefinitionRepository.save(newPositionDefinition);
    }



}
