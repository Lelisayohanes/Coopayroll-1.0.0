package com.lelisay.CooPayroll10.coremodule.user.registration;

public record RegistrationRequest(
         String email,
         String password,
         String firstName,
         String lastName,
         String role
) {


}
