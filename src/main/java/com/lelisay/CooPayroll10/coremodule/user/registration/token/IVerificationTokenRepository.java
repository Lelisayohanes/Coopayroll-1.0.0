package com.lelisay.CooPayroll10.coremodule.user.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IVerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);
}
