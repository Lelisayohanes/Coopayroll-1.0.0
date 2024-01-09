package com.lelisay.CooPayroll10.coremodule.subscription.payment;

import java.util.Optional;

public interface IPaymentGatewayService {
    PaymentGateway addPaymentGateway(PaymentGateway paymentGateway);

    Optional<PaymentGateway> findByPaymentAccount(String paymentAccount);
}
