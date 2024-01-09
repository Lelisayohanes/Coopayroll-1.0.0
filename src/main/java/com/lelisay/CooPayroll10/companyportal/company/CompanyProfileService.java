package com.lelisay.CooPayroll10.companyportal.company;

import com.lelisay.CooPayroll10.generalmodule.address.Address;
import com.lelisay.CooPayroll10.generalmodule.address.AddressService;
import com.lelisay.CooPayroll10.coremodule.subscription.billing.Billing;
import com.lelisay.CooPayroll10.coremodule.subscription.billing.BillingService;
import com.lelisay.CooPayroll10.companyportal.companysubscription.CompanySubscription;
import com.lelisay.CooPayroll10.companyportal.companysubscription.CompanySubscriptionService;
import com.lelisay.CooPayroll10.companyportal.request.CompanyProfileRequest;
import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import com.lelisay.CooPayroll10.generalmodule.contact.ContactService;
import com.lelisay.CooPayroll10.generalmodule.emailservice.subscription.SubscriptionEmailService;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.InvoiceService;
import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGateway;
import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGatewayService;
import com.lelisay.CooPayroll10.coremodule.subscription.subscription.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyProfileService implements ICompanyProfileService {
    private final ICompanyProfileRepository companyProfileRepository;
    private final CompanySubscriptionService companySubscriptionService;
    private final InvoiceService invoiceService;
    private final AddressService addressService;
    private final ContactService contactService;
    private final SubscriptionEmailService subscriptionEmailService;
    private final BillingService billingService;
    private final PaymentGatewayService paymentGatewayService;
    @Transactional
    public ResponseEntity<CompanyProfileRequest> saveCompanyProfile(CompanyProfileRequest companyProfileRequest) {
        try {
            // 1. save company info
            CompanyProfile newCompany = companyProfileRequest.getCompanyProfile();
            //check if duplicates
            String exist = checkIfDuplicated(companyProfileRequest.getCompanyProfile(), companyProfileRequest.getCompanyProfile().getActiveContact(), companyProfileRequest.getCompanyProfile().getActivePaymentGateway());
            CompanyProfile savedCompany = addCompanyProfile(newCompany);

            // 2. save address of company
            Address newAddress = companyProfileRequest.getCompanyProfile().getActiveAddress();
            Address savedAddress = addingCompanyAddress(newAddress, savedCompany);

            // 3. save contact of company
            Contact newContact = companyProfileRequest.getCompanyProfile().getActiveContact();
            Contact savedContact = addingCompanyContact(savedCompany,newContact);

            // 4. update company with address and contact
            CompanyProfile updatedCompany = updateCompanyProfile(savedCompany,savedAddress,savedContact);

            // 5. do company subscription
            SubscriptionPlan newPlan = companyProfileRequest.getSubscriptionPlan();
            CompanySubscription savedCompanySubscription = addCompanySubscription(newPlan, updatedCompany);

            // 6. initiate invoice
            int duration = companyProfileRequest.getDuration();
            Invoice savedInvoice = addInvoice(duration,updatedCompany,newPlan);

            // 7. adjust payment gateway
            PaymentGateway newPaymentGateway = companyProfileRequest.getCompanyProfile().getActivePaymentGateway();
                                                 newPaymentGateway.setCompanyProfile(savedCompany);
            PaymentGateway savedPaymentGateway  = paymentGatewayService.addPaymentGateway(newPaymentGateway);

            // 8. billing write
            Billing savedBilling = addNewBilling(savedInvoice,savedPaymentGateway,newPlan);

            // 9 .update saved invoice with added billing
            Invoice updatedInvoice = updateInvoice(savedInvoice, savedBilling);

            // 10. send email
            String userEmail = companyProfileRequest.getCompanyProfile().getActiveContact().getEmail();
            sendNotificationMail(userEmail);
            // 11. return built request return value

//            return ResponseEntity.status(HttpStatus.OK).body();
            CompanyProfileRequest sentRequest = new CompanyProfileRequest();
            CompanyProfile sentCompany = new CompanyProfile();

            sentCompany.getCompanyName();
            sentRequest.getDuration();
            sentRequest.setCompanyProfile(sentCompany);
            return ResponseEntity.status(HttpStatus.OK).body(sentRequest);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("Error in saveCompanyProfile: " + e.getMessage(), e);

            // Handle exceptions and return an appropriate HTTP response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String checkIfDuplicated(CompanyProfile companyProfile, Contact activeContact, PaymentGateway activePaymentGateway) {
        CompanyProfile companyProfileName = findByCompanyName(companyProfile.getCompanyName());
        CompanyProfile companyProfileBrandName = findByCompanyBrandName(companyProfile.getCompanyBrandName());
        CompanyProfile companyProfileCode = findByCompanyCode(companyProfile.getCompanyCode());
        Optional<Contact> activeContactEmail = contactService.findByEmail(activeContact.getEmail());
        Optional<Contact> activeContactPhone = contactService.findByPhone(activeContact.getPhone());
        Optional<PaymentGateway> activePaymentGatewayAccount = paymentGatewayService.findByPaymentAccount(activePaymentGateway.getPaymentAccount());

        // Check for null or empty values
        if (isNullOrEmpty(companyProfileName)) {
            return "Company name is null or empty";
        }

        if (isNullOrEmpty(companyProfileBrandName)) {
            return "Company brand name is null or empty";
        }

        if (isNullOrEmpty(companyProfileCode)) {
            return "Company code is null or empty";
        }

        if (isNullOrEmpty(activeContactEmail)) {
            return "Active contact email is null or empty";
        }

        if (isNullOrEmpty(activeContactPhone)) {
            return "Active contact phone is null or empty";
        }

        if (isNullOrEmpty(activePaymentGatewayAccount)) {
            return "Active payment gateway account is null or empty";
        }

        // Rest of your code...

        return "valid";
    }

    private boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        // Add more checks for other types if needed...

        return false;
    }

    private void sendNotificationMail(String userEmail) {
        String subject = "Application Accepted";
        String message = "Thank you for your subscription.";
        String header = "Coopayroll";
        String footer = "<p>Follow us on <a href=\"https://twitter.com/Coopayroll\">Twitter</a></p>";

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html><body>");
        emailContent.append("<div style=\"background-color: #f2f2f2; padding: 20px;\">"); // Adding a background color and padding for the header
        emailContent.append("<h1>").append(header).append("</h1>");
        emailContent.append("</div>");
        emailContent.append("<div style=\"padding: 20px;\">"); // Adding padding for the content
        emailContent.append("<h2>").append(subject).append("</h2>");
        emailContent.append("<p>").append(message).append("</p>");
        emailContent.append("</div>");
        emailContent.append("<div style=\"background-color: #f2f2f2; padding: 20px;\">"); // Adding a background color and padding for the footer
        emailContent.append(footer);
        emailContent.append("</div>");
        emailContent.append("</body></html>");

        subscriptionEmailService.sendSubscriptionEmailMessage(userEmail, subject, emailContent.toString());
    }


    private Invoice updateInvoice(Invoice savedInvoice, Billing savedBilling) {
        if (savedInvoice.getId() != null) {
            // Fetch the existing record from the database
            Optional<Invoice> existingInvoiceOptional = invoiceService.findById(savedInvoice.getId());

            // Check if the record exists
            if (existingInvoiceOptional.isPresent()) {
                // Update the existing record with the new data
                Invoice existingInvoice = existingInvoiceOptional.get();
                existingInvoice.setBilling(savedBilling);
                // Set other fields as needed

                // Save the updated record
                return invoiceService.updateInvoiceBilling(existingInvoice);
            } else {
                // The record with the given ID does not exist
                return null;
            }
        } else {
            // The entity doesn't have an ID, return null (indicating it hasn't been saved before)
            return null;
        }
    }

    private Billing addNewBilling(Invoice savedInvoice, PaymentGateway savedPaymentGateway,SubscriptionPlan subscriptionPlan) {
        Billing newBilling = new Billing();
        newBilling.setBillingCode(getBillingCode());
        newBilling.setTotalAmount(calculateAmount(subscriptionPlan.getPrice(),savedInvoice.getDuration()));
        newBilling.setConfirmed(false);
        newBilling.setInvoice(savedInvoice);
        newBilling.setPaymentGateway(savedPaymentGateway);
        return billingService.payBilling(newBilling);
    }


    private Invoice addInvoice(int duration, CompanyProfile updatedCompany, SubscriptionPlan newPlan) {
        Invoice newInvoice = new Invoice();
        newInvoice.setSubscriptionPlan(newPlan);
        newInvoice.setApproved(false);
        newInvoice.setCompanyProfile(updatedCompany);
        newInvoice.setDuration(duration);
        return invoiceService.addNewInvoice(newInvoice);
    }

    private CompanySubscription addCompanySubscription(SubscriptionPlan newPlan, CompanyProfile updatedCompany) {
        CompanySubscription newCompanySubscription = new CompanySubscription();
        newCompanySubscription.setSubscriptionPlan(newPlan);
        newCompanySubscription.setCompanyProfile(updatedCompany);
        newCompanySubscription.setActive(true);
        return companySubscriptionService.saveCompanySubscription(newCompanySubscription);
    }

    @Override
    public CompanyProfile updateCompanyProfile(CompanyProfile savedCompany, Address savedAddress, Contact savedContact) {

        if (savedCompany.getId() != null) {
            // Fetch the existing record from the database
            CompanyProfile existingCompany = companyProfileRepository.findById(savedCompany.getId()).orElse(null);
            // Check if the record exists
            if (existingCompany != null) {
                // Update the existing record with the new data
                existingCompany.setActiveAddress(savedAddress);
                existingCompany.setActiveContact(savedContact);
                // Set other fields as needed

                // Save the updated record
                return companyProfileRepository.save(existingCompany);
            } else {
                // The record with the given ID does not exist
                return null;
            }
        } else {
            // The entity doesn't have an ID, return null (indicating it hasn't been saved before)
            return null;
        }
    }

    private Contact addingCompanyContact(CompanyProfile savedCompany, Contact activeContact) {
        activeContact.setCompanyProfile(savedCompany);
        return contactService.addContactOfCompany(activeContact);
    }

    private Address addingCompanyAddress(Address activeAddress, CompanyProfile savedCompany) {
        activeAddress.setCompanyProfile(savedCompany);
        return addressService.addAddressOfCompany(activeAddress);
    }

    private String getBillingCode() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 8; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

    private Float calculateAmount(Float price, int duration) {
        return price*duration;
    }

    private CompanyProfile addCompanyProfile(CompanyProfile newProfile) {
        return companyProfileRepository.save(newProfile);
    }

    @Override
    public CompanyProfile getCompanyProfileById(Long companyId) {
        System.out.println("inservice");
        return companyProfileRepository.findById(companyId).orElse(null);
    }

    //if company change the address
    @Override
    public CompanyProfile updateAddressOfCompanyProfile(Long companyId, CompanyProfile updatedCompanyProfile) {
        return null;
    }

    //if the company change the contact
    @Override
    public CompanyProfile updateContactOfCompanyProfile(Long companyId, CompanyProfile updatedCompanyProfile) {
        return null;
    }

    @Override
    public void deleteCompanyProfile(Long companyId) {

    }

    @Override
    public CompanyProfile findByCompanyName(String companyName) {
        return companyProfileRepository.findByCompanyName(companyName);
    }

    @Override
    public CompanyProfile findByCompanyBrandName(String companyBrandName) {
        return companyProfileRepository.findByCompanyBrandName(companyBrandName);
    }

    @Override
    public Long countEmployees(Long companyId) {
        return null;
    }

    @Override
    public List<CompanyProfile> getAllCompanyProfiles() {
        return null;
    }

    @Override
    public List<CompanyProfile> findCompaniesWithMoreEmployeesThan(int employeeCount) {
        return null;
    }

    @Override
    public CompanyProfile findByCompanyCode(String companyCode) {
        return companyProfileRepository.findByCompanyCode(companyCode);
    }

    @Override
    public CompanyProfile getCompanyProfileByCompanyCode(String companyCode) {
        return companyProfileRepository.findByCompanyCode(companyCode);
    }
}
