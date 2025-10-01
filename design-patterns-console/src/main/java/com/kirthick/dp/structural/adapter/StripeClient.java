package com.kirthick.dp.structural.adapter;

import com.kirthick.dp.common.exceptions.TransientException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// Simulated third-party client with its own API shape (paise, returns txn id or throws)
public final class StripeClient {
    public String chargeInPaise(long amountPaise, String user) {
        if (ThreadLocalRandom.current().nextInt(100) < 25) { // 25% transient failure
            throw new TransientException("Stripe: network glitch");
        }
        if (amountPaise <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        return UUID.randomUUID().toString();
    }
}
