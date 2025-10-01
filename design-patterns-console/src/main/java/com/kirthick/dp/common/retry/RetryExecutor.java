package com.kirthick.dp.common.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public final class RetryExecutor {
    private static final Logger log = LoggerFactory.getLogger(RetryExecutor.class);

    private RetryExecutor() {}

    public static <T> T execute(Callable<T> action, RetryPolicy policy, String operationName) throws Exception {
        int attempt = 0;
        Throwable last = null;
        while (attempt < policy.maxAttempts) {
            try {
                attempt++;
                return action.call();
            } catch (Throwable t) {
                last = t;
                if (!policy.retryOn.test(t) || attempt >= policy.maxAttempts) {
                    if (t instanceof Exception e) throw e;
                    throw new Exception(t);
                }
                Duration delay = computeBackoff(policy.baseDelay, policy.maxDelay, attempt);
                log.warn("Transient failure in {} (attempt {}/{}): {}. Retrying in {} ms",
                        operationName, attempt, policy.maxAttempts, t.toString(), delay.toMillis());
                sleep(delay);
            }
        }
        if (last instanceof Exception e) throw e;
        throw new Exception(last);
    }

    private static Duration computeBackoff(Duration base, Duration max, int attempt) {
        long exp = (long) (base.toMillis() * Math.pow(2, attempt - 1));
        long jitter = ThreadLocalRandom.current().nextLong(0, base.toMillis() + 1);
        long delay = Math.min(max.toMillis(), exp + jitter);
        return Duration.ofMillis(Math.max(0, delay));
    }

    private static void sleep(Duration d) {
        try {
            Thread.sleep(d.toMillis());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
