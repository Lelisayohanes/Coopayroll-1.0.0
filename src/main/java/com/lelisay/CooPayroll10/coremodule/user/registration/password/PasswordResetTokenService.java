package com.lelisay.CooPayroll10.coremodule.user.registration.password;

import com.lelisay.CooPayroll10.coremodule.user.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForUser(Optional<User> user, String passwordToken){

        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user.get());
        passwordResetTokenRepository.save(passwordResetToken);

    }

    public String validatePasswordResetToken(String theToken){
            PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);

            if(token == null){
                return "invalid password reset token ";
            }

            //get user from token
            User user = token.getUser();
            Calendar calendar = Calendar.getInstance();

            if((token.getExpirationTime().getTime() - calendar.getTime().getTime())<= 0){
                //verificationTokenRepository.delete(token);
                return "token already expired, " +
                        "pleased click link below to receive new password reset link";
            }
            return "valid";
    }

    //check if user exist for requested token
    public Optional<User> findUserByPasswordToken(String passwordToken){
        return Optional.ofNullable(passwordResetTokenRepository
                .findByToken(passwordToken).getUser());
    };




}
