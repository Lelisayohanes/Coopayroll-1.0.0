package com.lelisay.CooPayroll10.coremodule.user.security.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String userName;
    private String password;
}
