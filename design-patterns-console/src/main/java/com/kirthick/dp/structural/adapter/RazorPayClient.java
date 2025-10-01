package com.kirthick.dp.structural.adapter;

import com.kirthick.dp.common.exceptions.TransientException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// Simulated third-party client with its own API shape (rupees, boolean success)
public final class RazorPayClient {
    public boolean pay(double rupees, String customerRef) {
        // random transient failure
        if (ThreadLocalRandom.current().nextInt(100) < 20) { // 20% transient failure
            throw new TransientException("RazorPay: temporary gateway issue");
        }
        // random business failure
        if (rupees > 50000) {
            return false;
        }
        return true;
    }

    public String lastTransactionId() {
        return UUID.randomUUID().toString();
    }
}
