package com.kirthick.dp.structural.adapter;

public final class StripeAdapter implements PaymentProcessor {
    private final StripeClient client = new StripeClient();

    @Override
    public PaymentResult process(PaymentRequest request) {
        long paise = Math.round(convertToINR(request.currency, request.amount) * 100);
        String txn = client.chargeInPaise(paise, request.userId);
        return new PaymentResult(PaymentStatus.SUCCESS, txn, "Paid via Stripe");
    }

    private static double convertToINR(String currency, double amount) {
        switch (currency) {
            case "INR": return amount;
            case "USD": return amount * 83.0; // sample rate
            default: return amount; // simplistic default
        }
    }
}
