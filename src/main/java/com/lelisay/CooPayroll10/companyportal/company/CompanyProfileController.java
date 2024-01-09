package com.lelisay.CooPayroll10.companyportal.company;

import com.lelisay.CooPayroll10.companyportal.request.CompanyProfileRequest;
import com.lelisay.CooPayroll10.generalmodule.emailservice.subscription.SubscriptionEmailService;
import com.lelisay.CooPayroll10.generalmodule.exception.ThisEmailNotRegisteredException;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.InvoiceService;
import com.lelisay.CooPayroll10.generalmodule.response.CustomErrorResponse;
import com.lelisay.CooPayroll10.coremodule.user.user.User;
import com.lelisay.CooPayroll10.coremodule.user.user.UserService;
import com.lelisay.CooPayroll10.coremodule.user.companyuser.RequestNewCompanyUser;
import com.lelisay.CooPayroll10.coremodule.user.registration.RegisterCompanyRequest;
import com.lelisay.CooPayroll10.generalmodule.response.CustomSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyProfileController {
    private final CompanyProfileService companyProfileService;
    private final InvoiceService invoiceService;
    private final UserService userService;
    private final SubscriptionEmailService emailService;


    @GetMapping(value = "/{id}")
    public ResponseEntity getCompanyById(@PathVariable Long id){
        CompanyProfile companyProfile = companyProfileService.getCompanyProfileById(id);
        if(companyProfile==null){
            int statusCode = 404; // or any other appropriate status code
            String errorMessage = "Entity with ID " + id + " not found.";
            String requestInformation = "Request: GET /api/entities/" + id;
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        CompanyProfileResponseDTO responseDTO = CompanyProfileResponseDTO.fromEntity(companyProfile);
        return ResponseEntity.ok(responseDTO);

    }


    @GetMapping(value="/code")
    public ResponseEntity getCompanyByCompanyCode(@RequestParam("code") String companyCode){
        CompanyProfile companyProfile = companyProfileService.getCompanyProfileByCompanyCode(companyCode);
        if(companyProfile==null){
            int statusCode = 404; // or any other appropriate status code
            String errorMessage = "Entity with ID " + companyCode + " not found.";
            String requestInformation = "Request: GET /api/entities/" + companyCode;
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyProfile, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> addNewCompany(
            @RequestBody CompanyProfileRequest request,
            final HttpServletRequest httpServletRequest) {

        CompanyProfileRequest processedData = companyProfileService.saveCompanyProfile(request).getBody();

        if (processedData == null) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    400,
                    "Bad Request",
                    "Company registration failed"
            );

            return ResponseEntity.status(400).body(errorResponse);
        }
        var data = request.getCompanyProfile().getCompanyName();

        // Business logic for success case
        CustomSuccessResponse successResponse = new CustomSuccessResponse(200, "Successfully registered",data );
        return ResponseEntity.ok(successResponse);
    }


    @PostMapping("/approve")
    public String approveRequestedCompany(@RequestParam("code") String companyCode) {
        //1 check if there is new request in the invoice with this company name and check if already approved
         CompanyProfile requestedCompany = companyProfileService.findByCompanyCode(companyCode);

         if(requestedCompany==null){
             return "no company with such id";
         }
         //check if invoice is approved
        var companyId = requestedCompany.getId();
        Invoice companyInvoice = invoiceService.getInvoiceWithCompanyId(companyId);
        if(companyInvoice==null){
            return "some thing went wrong";
        }
        //2 approve and register the company email to users
        companyInvoice.setApproved(true);
        invoiceService.approveInvoiceByAdmin(companyInvoice);

        RegisterCompanyRequest addUser = new RegisterCompanyRequest(
                companyCode,requestedCompany.getActiveContact().getEmail()
        );
        userService.registerCompanyUser(addUser);
        //3 send email it is approved
        // Send registration notification email

        String userEmail = requestedCompany.getActiveContact().getEmail();
        String subject = "Request Approved";
        String text = "Thank you for registering for our service" +
                "Your Application Approved Success fully go a head and login ";
        emailService.sendSubscriptionEmailMessage(userEmail, subject, text);
        log.info("Your request approved");

        return "succcessfully approved";
    }

    //fetch company registered and approved but no registered user
    @PostMapping("/company-user")
    public ResponseEntity registerCompanyAfterApproval(@RequestBody RequestNewCompanyUser user) throws ThisEmailNotRegisteredException {
        //1 check if user exist or not
        Optional<User> addedUser = userService.findByEmail(user.email());
        if(addedUser==null){
            int statusCode = 404; // or any other appropriate status code
            String errorMessage = "User with ID " + addedUser.get().getEmail() + " not Added yet.";
            String requestInformation = "Request: POST /api/v1/company-user/" ;
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        //2 check if user setted password and enabled
        if(addedUser.get().getPassword() != null && addedUser.get().isEnabled()){
            int statusCode = 200; // or any other appropriate status code
            String errorMessage = "this user is already enabled you can login now.";
            String requestInformation = "Request: POST /api/v1/company-user/" ;
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
            //3. check if password setted but not enabled
        } else if (addedUser.get().getPassword()!=null && !(addedUser.get().isEnabled())) {
            int statusCode = 200; // or any other appropriate status code
            String errorMessage = "Check your email and enable it you already registered";
            String requestInformation = "Request: POST /api/v1/company-user/" ;
            CustomErrorResponse errorResponse = new CustomErrorResponse(statusCode, errorMessage, requestInformation);
            return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
        } else {

            //register as user
            User newAddedUser = userService.addUserAfterSubscription(user);
            //send email
            String userEmail = user.email();
            String subject = "Verify your Email";
            String text = "Once you verify your email you can login and explore new payroll wordl with " +
                    "Coopayroll ";
            emailService.sendSubscriptionEmailMessage(userEmail, subject, text);
            log.info("Your request approved");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


}
