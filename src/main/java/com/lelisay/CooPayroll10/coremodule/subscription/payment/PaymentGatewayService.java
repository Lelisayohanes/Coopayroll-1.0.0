package com.lelisay.CooPayroll10.coremodule.subscription.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentGatewayService implements IPaymentGatewayService{
    private final IPaymentGatewayRepository paymentGatewayRepository;
    @Override
    public PaymentGateway addPaymentGateway(PaymentGateway paymentGateway) {
        return paymentGatewayRepository.save(paymentGateway);
    }
    @Override
    public Optional<PaymentGateway> findByPaymentAccount(String paymentAccount) {
        return paymentGatewayRepository.findByPaymentAccount (paymentAccount);
    }

}
