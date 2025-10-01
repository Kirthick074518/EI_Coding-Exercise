package com.kirthick.dp.common.exceptions;

public class TransientException extends AppException {
    public TransientException(String message) { super(message); }
    public TransientException(String message, Throwable cause) { super(message, cause); }
}
