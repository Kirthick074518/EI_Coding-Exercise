package com.kirthick.dp.structural.adapter;

import com.kirthick.dp.common.Validation;

public final class PaymentRequest {
    public final String userId;
    public final String currency; // ISO code, e.g., INR, USD
    public final double amount;   // amount in currency units

    public PaymentRequest(String userId, String currency, double amount) {
        this.userId = Validation.requireNonBlank(userId, "userId");
        this.currency = Validation.requireNonBlank(currency, "currency").toUpperCase();
        Validation.require(amount > 0, "amount must be > 0");
        this.amount = amount;
    }
}
