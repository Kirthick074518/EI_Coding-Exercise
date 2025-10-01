package ex1.structural.adapter;

public final class AdapterMain {
    public static void main(String[] args) {
        System.out.println("-- Adapter Pattern --");
        PaymentRequest r1 = new PaymentRequest("user-123", "USD", 10.50);
        PaymentRequest r2 = new PaymentRequest("user-987", "INR", 499.00);

        PaymentProcessor rp = new RazorPayAdapter(new RazorPayClient());
        PaymentProcessor sp = new StripeAdapter(new StripeClient());

        System.out.println("RazorPay: " + rp.process(r2));
        System.out.println("Stripe  : " + sp.process(r1));
        System.out.println();
    }
}
