package com.lelisay.CooPayroll10.companyportal.departmentdefinition;

import com.lelisay.CooPayroll10.companyportal.company.CompanyProfile;
import com.lelisay.CooPayroll10.companyportal.company.CompanyProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentDefinitionService implements IDepartmentDefinitionService {

   private final IDepartmentDefinitionRepository departmentDefinitionRepository;
   private final CompanyProfileService companyProfileService;
    @Override
    public List<DepartmentDefinition> getCompanyDepartment() {
        return departmentDefinitionRepository.findAll();
    }
    @Override
    public DepartmentDefinition saveNewDepartment(DepartmentDefinition newDepartmentDefinition, Long companyId) {
        log.info("new department {} to be saved", companyId );
        CompanyProfile companyProfile = companyProfileService.getCompanyProfileById(companyId);
        newDepartmentDefinition.setCompanyProfile(companyProfile);
        return departmentDefinitionRepository.save(newDepartmentDefinition);
    }

}
