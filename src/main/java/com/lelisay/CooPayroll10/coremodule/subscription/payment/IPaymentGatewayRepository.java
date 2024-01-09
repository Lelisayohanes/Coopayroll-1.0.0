package com.lelisay.CooPayroll10.coremodule.subscription.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPaymentGatewayRepository extends JpaRepository<PaymentGateway, Long> {

   Optional<PaymentGateway> findByPaymentAccount (String paymentAccount);
}
