package com.lelisay.CooPayroll10.coremodule.user.registration;

import com.lelisay.CooPayroll10.coremodule.user.registration.token.VerificationToken;
import com.lelisay.CooPayroll10.coremodule.user.user.UserService;
import com.lelisay.CooPayroll10.coremodule.user.user.User;
import com.lelisay.CooPayroll10.coremodule.user.registration.password.PasswordResetRequest;
import com.lelisay.CooPayroll10.coremodule.user.registration.token.IVerificationTokenRepository;
import com.lelisay.CooPayroll10.generalmodule.event.RegistrationCompleteEvent;
import com.lelisay.CooPayroll10.generalmodule.event.listener.RegistrationCompleteEventListener;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {
    private final UserService userService;
    private final RegistrationCompleteEventListener listener;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final HttpServletRequest servletRequest;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest httpServletRequest){
        User user =  userService.registerUser(registrationRequest);
        //create event publisher
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(httpServletRequest)));
        return "successfully registered! please check your email to complete registration";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token){
//        String url = applicationUrl+"/api/v1/register/verify-email?token="+verificationToken.getToken();

        String url = applicationUrl(servletRequest)+"/api/v1/register/resend-verification-token?token="+token;
        VerificationToken theToken = verificationTokenRepository.findByToken(token);

        if(theToken.getUser().isEnabled()){
            return "this account has already been verified, please login";
        }
        String verificationResult = userService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification code token, " +
                "<a href=\""+url+ "\"> Get new verification Link.</a>";
    }

    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                         final HttpServletRequest httpServletRequest
    ) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        log.info(oldToken,"old verification");
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser,applicationUrl(httpServletRequest), verificationToken);
        return "A new verification link has been sent to email check your email," +
                " Please check to Activate your account";
    }

    private void resendVerificationTokenEmail(User theUser,
                                              String applicationUrl,
                                              VerificationToken verificationToken
    ) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/register/verify-email?token="+verificationToken.getToken();
        listener.sendVerificationEmail(url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest( @RequestBody PasswordResetRequest passwordResetRequest,
                                        final HttpServletRequest httpServletRequest
    ){
        Optional<User> user = userService.findByEmail(passwordResetRequest.getEmail());

        String passwordResetUrl = "";
        if(user.isPresent()){
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(),passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(),
                    applicationUrl(httpServletRequest),
                    passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user,
                                          String applicationUrl,
                                          String passwordResetToken) {
        String url = applicationUrl +"/api/v1/register/reset-password?token="+passwordResetToken;
        try {
            listener.sendPasswordResetEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String passwordResetToken){
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);

        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "invalid password reset token";
        }
        User user = userService.findUserByPasswordResetToken(passwordResetToken);
        if(user!=null){
            userService.resetUserPassword(user,passwordResetRequest.getNewPassword());
            return "password has been reset successfully ";
        }
        return "invalid password reset token";
    }


    public String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }

}
