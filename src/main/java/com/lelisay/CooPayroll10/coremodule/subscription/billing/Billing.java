package com.lelisay.CooPayroll10.coremodule.subscription.billing;

import com.lelisay.CooPayroll10.coremodule.subscription.payment.PaymentGateway;
import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String billingCode;
    private Float totalAmount;
    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "payment_gateway_id")
    private PaymentGateway paymentGateway;

    @OneToOne(mappedBy = "billing", cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")  // Add this line
    private Invoice invoice;

    private Date paymentDate;

    // Getters and setters...
}
