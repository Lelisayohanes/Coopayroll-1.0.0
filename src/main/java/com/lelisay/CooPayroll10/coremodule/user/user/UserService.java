package com.lelisay.CooPayroll10.coremodule.user.user;

import com.lelisay.CooPayroll10.coremodule.user.registration.token.VerificationToken;
import com.lelisay.CooPayroll10.coremodule.user.exception.UserAlreadyExistsException;
import com.lelisay.CooPayroll10.coremodule.user.registration.RegisterCompanyRequest;
import com.lelisay.CooPayroll10.coremodule.user.registration.RegistrationRequest;
import com.lelisay.CooPayroll10.coremodule.user.registration.password.PasswordResetTokenService;
import com.lelisay.CooPayroll10.coremodule.user.registration.token.IVerificationTokenRepository;
import com.lelisay.CooPayroll10.generalmodule.exception.ThisEmailNotRegisteredException;
import com.lelisay.CooPayroll10.generalmodule.response.CustomErrorResponse;
import com.lelisay.CooPayroll10.coremodule.user.role.Role;
import com.lelisay.CooPayroll10.coremodule.user.role.RoleRepository;
import com.lelisay.CooPayroll10.coremodule.user.companyuser.RequestNewCompanyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private  final IUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;



//    @Override
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }

//find all users
    @Override
    public List<UserRecord> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserRecord(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        new HashSet<>(user.getRoles()))).collect(Collectors.toList());
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        Optional<User> user = userRepository.findByEmail(registrationRequest.email());
            if(user.isPresent()){
                throw new UserAlreadyExistsException("user with this email  "
                        + registrationRequest.email()
                        + "  already exist");
            }

            var newUser = new User();
            newUser.setFirstName(registrationRequest.firstName());
            newUser.setLastName(registrationRequest.lastName());
            newUser.setEmail(registrationRequest.email());
            newUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
            newUser.setCompanyCode("0");
            Role role = roleRepository.findByName("ADMIN").get();
            newUser.setRoles(Collections.singletonList(role));

            return userRepository.save(newUser);
    }

    @Override
    public User addUserAfterSubscription(RequestNewCompanyUser registrationRequest) throws ThisEmailNotRegisteredException {
        String userEmail = registrationRequest.email();

        // Check if a user with the given email exists
        Optional<User> existingUser = userRepository.findByEmail(userEmail);

        if (!(existingUser.isPresent())) {
            // Update the existing user
              new CustomErrorResponse(
                    404,
                    "user not registered",
                    "jhdsd"
            );
        }
        User userToUpdate = existingUser.get();
        userToUpdate.setCompanyCode(registrationRequest.companyCode());
        userToUpdate.setPassword(passwordEncoder.encode(registrationRequest.password()));
        userToUpdate.setLastName(registrationRequest.firstname());
        userToUpdate.setFirstName(registrationRequest.lastname());

        // Save the updated user
        return userRepository.save(userToUpdate);

    }

    @Override
    public User registerCompanyUser(RegisterCompanyRequest registerCompanyRequest) {
        Optional<User> user = userRepository.findByEmail(registerCompanyRequest.email());
        //check if present and not null or null
        if(user.isPresent()){
            throw new UserAlreadyExistsException("user with this email  "
                    + registerCompanyRequest.email()
                    + "  already exist");
        }

        var newUser = new User();
        newUser.setEmail(registerCompanyRequest.email());
        Role role = roleRepository.findByName("COMPANY").get();
        newUser.setRoles(Collections.singletonList(role));
        newUser.setCompanyCode(registerCompanyRequest.companyCode());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(theToken);
        if(token == null){
            return "invalid verification token ";
        }
        //get user from token
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();

        if((token.getExpirationTime().getTime() - calendar.getTime().getTime())<= 0){
            //verificationTokenRepository.delete(token);
            return "token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken theToken = verificationTokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        theToken.setToken(UUID.randomUUID().toString());
        theToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        return verificationTokenRepository.save(theToken);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(Optional.ofNullable(user),passwordToken);
    }

    @Override
    public User findUserByPasswordResetToken(String passwordResetToken) {
        return passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
         return passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    @Override
    public void saveUserVerificationToken(User newUser, String verificationToken) {
        var emailVerificationToken = new VerificationToken(verificationToken, newUser);
        verificationTokenRepository.save(emailVerificationToken);
    }

}
