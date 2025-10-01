package ex1.structural.adapter;

public interface PaymentProcessor {
    PaymentResult process(PaymentRequest request);
}
