package com.kirthick.dp.structural.adapter;

public final class RazorPayAdapter implements PaymentProcessor {
    private final RazorPayClient client = new RazorPayClient();

    @Override
    public PaymentResult process(PaymentRequest request) {
        double rupees = convertToINR(request.currency, request.amount);
        boolean ok = client.pay(rupees, request.userId);
        if (ok) {
            return new PaymentResult(PaymentStatus.SUCCESS, client.lastTransactionId(), "Paid via RazorPay");
        }
        return new PaymentResult(PaymentStatus.FAILURE, null, "RazorPay declined the payment");
    }

    private static double convertToINR(String currency, double amount) {
        switch (currency) {
            case "INR": return amount;
            case "USD": return amount * 83.0; // sample rate
            default: return amount; // simplistic default
        }
    }
}
