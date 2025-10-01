package com.kirthick.dp.common.retry;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Predicate;

public final class RetryPolicy {
    public final int maxAttempts;
    public final Duration baseDelay;
    public final Duration maxDelay;
    public final Predicate<Throwable> retryOn;

    private RetryPolicy(Builder b) {
        this.maxAttempts = b.maxAttempts;
        this.baseDelay = b.baseDelay;
        this.maxDelay = b.maxDelay;
        this.retryOn = b.retryOn;
    }

    public static Builder newBuilder() { return new Builder(); }

    public static final class Builder {
        private int maxAttempts = 3;
        private Duration baseDelay = Duration.ofMillis(200);
        private Duration maxDelay = Duration.ofSeconds(2);
        private Predicate<Throwable> retryOn = t -> true;

        public Builder maxAttempts(int maxAttempts) {
            this.maxAttempts = Math.max(1, maxAttempts); return this;
        }
        public Builder baseDelay(Duration baseDelay) {
            this.baseDelay = Objects.requireNonNull(baseDelay); return this;
        }
        public Builder maxDelay(Duration maxDelay) {
            this.maxDelay = Objects.requireNonNull(maxDelay); return this;
        }
        public Builder retryOn(Predicate<Throwable> retryOn) {
            this.retryOn = Objects.requireNonNull(retryOn); return this;
        }
        public RetryPolicy build() { return new RetryPolicy(this); }
    }
}
