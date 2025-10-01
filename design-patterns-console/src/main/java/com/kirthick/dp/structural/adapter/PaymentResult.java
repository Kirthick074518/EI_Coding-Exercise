package com.kirthick.dp.structural.adapter;

public final class PaymentResult {
    public final PaymentStatus status;
    public final String transactionId;
    public final String message;

    public PaymentResult(PaymentStatus status, String transactionId, String message) {
        this.status = status;
        this.transactionId = transactionId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "status=" + status +
                ", transactionId='" + transactionId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
