package ex1.structural.adapter;

import java.util.UUID;

// Simulated third-party client with a different API shape
public final class StripeClient {
    // Returns true/false and fills txn id via one-element array; amounts in cents
    public boolean chargeCents(String customer, long cents, String currency, String[] outTxnId) {
        if (cents <= 0) return false;
        String id = "stripe_" + UUID.randomUUID();
        if (outTxnId != null && outTxnId.length > 0) outTxnId[0] = id;
        return true;
    }
}
