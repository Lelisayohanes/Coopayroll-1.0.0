package com.lelisay.CooPayroll10.companyportal.gradedefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeDefinitionService implements IGradeDefinitionService{
    private final CompanyProfileService companyProfileService;
    private final IGradeDefinitionRepository gradeDefinitionRepository;
    @Override
    public List<GradeDefinition> getCompanyGrade() {
        return gradeDefinitionRepository.findAll();
    }

    @Override
    public GradeDefinition saveNewGrade(GradeDefinition newGradeDefinition, Long companyId) {
        log.info("new department {} to be saved", companyId );
        CompanyProfile companyProfile = companyProfileService.getCompanyProfileById(companyId);
        newGradeDefinition.setCompanyProfile(companyProfile);
        return gradeDefinitionRepository.save(newGradeDefinition);
    }
}
