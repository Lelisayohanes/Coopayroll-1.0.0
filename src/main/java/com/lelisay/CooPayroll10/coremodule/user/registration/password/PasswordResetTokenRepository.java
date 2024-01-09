package com.lelisay.CooPayroll10.coremodule.user.registration.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken , Long> {

    PasswordResetToken findByToken(String theToken);
}
