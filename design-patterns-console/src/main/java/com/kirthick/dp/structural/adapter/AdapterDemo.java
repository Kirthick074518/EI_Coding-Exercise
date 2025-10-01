package com.kirthick.dp.structural.adapter;

import com.kirthick.dp.app.ConsoleIO;
import com.kirthick.dp.common.Validation;

public final class AdapterDemo {
    public static void run(ConsoleIO io) {
        io.println("-- Adapter Pattern: Payment Gateways with Retry --");
        String gateway = io.readLine("Gateway (razorpay/stripe): ");
        String userId = io.readLine("User ID: ");
        String currency = io.readLine("Currency (INR/USD): ");
        String amtStr = io.readLine("Amount: ");
        try {
            double amount = Double.parseDouble(Validation.requireNonBlank(amtStr, "amount"));
            PaymentProcessor processor = "stripe".equalsIgnoreCase(gateway) ? new StripeAdapter() : new RazorPayAdapter();
            PaymentService service = new PaymentService(processor);
            PaymentResult result = service.pay(new PaymentRequest(userId, currency, amount));
            io.println("Result: " + result);
        } catch (Exception e) {
            io.println("Payment failed: " + e.getMessage());
        }
    }
}
