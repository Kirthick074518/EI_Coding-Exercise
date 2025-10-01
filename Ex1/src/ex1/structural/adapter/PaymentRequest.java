package ex1.structural.adapter;

public final class PaymentRequest {
    public final String userId;
    public final String currency; // e.g., INR, USD
    public final double amount;   // major units (e.g., 10.50)

    public PaymentRequest(String userId, String currency, double amount) {
        this.userId = userId;
        this.currency = currency;
        this.amount = amount;
    }
}
