package com.lelisay.CooPayroll10.coremodule.user.companyuser;

public record RequestNewCompanyUser(
        String companyCode,
        String email,
        String password,
        String lastname,
        String firstname

) {
}
