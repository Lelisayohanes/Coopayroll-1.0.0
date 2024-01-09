package com.lelisay.CooPayroll10.coremodule.user.user;

import com.lelisay.CooPayroll10.coremodule.user.companyuser.RequestNewCompanyUser;
import com.lelisay.CooPayroll10.coremodule.user.registration.RegisterCompanyRequest;
import com.lelisay.CooPayroll10.coremodule.user.registration.token.VerificationToken;
import com.lelisay.CooPayroll10.generalmodule.exception.ThisEmailNotRegisteredException;
import com.lelisay.CooPayroll10.coremodule.user.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    //get list od user
    List<UserRecord> getUsers();
    //add user
    User registerUser(RegistrationRequest registrationRequest);


    User addUserAfterSubscription(RequestNewCompanyUser registrationRequest) throws ThisEmailNotRegisteredException;

    User registerCompanyUser(RegisterCompanyRequest registerCompanyRequest) throws ThisEmailNotRegisteredException;
    //find user
    Optional<User> findByEmail(String email);

    String validatePasswordResetToken(String passwordResetToken);

    void saveUserVerificationToken(User newUser, String verificationToken);

    String validateToken(String theToken);
    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    User findUserByPasswordResetToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
}
