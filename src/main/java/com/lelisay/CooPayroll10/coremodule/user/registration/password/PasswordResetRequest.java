package com.lelisay.CooPayroll10.coremodule.user.registration.password;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PasswordResetRequest {
    private String email;
    private String newPassword;
    private String confirmPassword;
}
