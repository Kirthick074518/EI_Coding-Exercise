package ex1.structural.adapter;

public final class StripeAdapter implements PaymentProcessor {
    private final StripeClient client;
    public StripeAdapter(StripeClient client) { this.client = client; }

    @Override
    public PaymentResult process(PaymentRequest request) {
        long cents = Math.round(request.amount * 100.0);
        String[] out = new String[1];
        boolean ok = client.chargeCents(request.userId, cents, request.currency, out);
        if (ok) {
            return new PaymentResult(PaymentStatus.SUCCESS, out[0], "Paid via Stripe");
        }
        return new PaymentResult(PaymentStatus.FAILED, null, "Stripe payment failed");
    }
}
