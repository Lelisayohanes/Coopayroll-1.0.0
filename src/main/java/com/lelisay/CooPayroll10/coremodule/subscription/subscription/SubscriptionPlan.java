package com.lelisay.CooPayroll10.coremodule.subscription.subscription;

import com.lelisay.CooPayroll10.coremodule.subscription.invoice.Invoice;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;
    @Column(unique = true)
    private String planName;
    private String planDescription;
    private String paymentFrequency;
    private Float price;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL)
    private List<Invoice> invoices;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
