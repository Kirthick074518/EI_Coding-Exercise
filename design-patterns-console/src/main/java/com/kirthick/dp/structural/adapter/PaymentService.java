package com.kirthick.dp.structural.adapter;

import com.kirthick.dp.common.retry.RetryExecutor;
import com.kirthick.dp.common.retry.RetryPolicy;
import com.kirthick.dp.common.exceptions.TransientException;

import java.time.Duration;

public final class PaymentService {
    private final PaymentProcessor processor;
    private final RetryPolicy policy;

    public PaymentService(PaymentProcessor processor) {
        this.processor = processor;
        this.policy = RetryPolicy.newBuilder()
                .maxAttempts(4)
                .baseDelay(Duration.ofMillis(200))
                .maxDelay(Duration.ofSeconds(1))
                .retryOn(t -> t instanceof TransientException)
                .build();
    }

    public PaymentResult pay(PaymentRequest request) throws Exception {
        return RetryExecutor.execute(() -> processor.process(request), policy, "payment");
    }
}
