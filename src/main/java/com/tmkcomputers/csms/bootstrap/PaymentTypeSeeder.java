package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.PaymentType;
import com.tmkcomputers.csms.repository.PaymentTypeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class PaymentTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeSeeder(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedPaymentTypes();
    }

    private void seedPaymentTypes() {
        if (paymentTypeRepository.count() == 0) {
            List<PaymentType> paymentTypes = Arrays.asList(
                    new PaymentType(null, "Credit or Debit Card", "Payment made using a credit or Debit card"),
                    new PaymentType(null, "PayPal", "Payment made using PayPal"),
                    new PaymentType(null, "Bank Transfer", "Payment made via bank transfer"),
                    new PaymentType(null, "Cash", "Payment made using cash")
            );

            paymentTypeRepository.saveAll(paymentTypes);
        }
    }
}

