package ex1.structural.adapter;

public final class RazorPayAdapter implements PaymentProcessor {
    private final RazorPayClient client;
    public RazorPayAdapter(RazorPayClient client) { this.client = client; }

    @Override
    public PaymentResult process(PaymentRequest request) {
        int paise = (int) Math.round(request.amount * 100.0);
        String txn = client.payPaise(request.userId, paise, request.currency);
        if (txn != null) {
            return new PaymentResult(PaymentStatus.SUCCESS, txn, "Paid via RazorPay");
        }
        return new PaymentResult(PaymentStatus.FAILED, null, "RazorPay payment failed");
    }
}
