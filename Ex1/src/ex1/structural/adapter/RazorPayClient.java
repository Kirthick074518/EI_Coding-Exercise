package ex1.structural.adapter;

import java.util.UUID;

// Simulated third-party client with its own API shape
public final class RazorPayClient {
    // Expects amount in paise (1 INR = 100 paise)
    public String payPaise(String userId, int amountPaise, String currency) {
        if (amountPaise <= 0) return null;
        // pretend to succeed and return a transaction id
        return "razor_" + UUID.randomUUID();
    }
}
