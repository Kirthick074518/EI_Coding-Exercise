package com.kirthick.dp.structural.adapter;

public interface PaymentProcessor {
    PaymentResult process(PaymentRequest request) throws Exception;
}
