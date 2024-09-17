package com.DXvalley.chatbot.exception.customException;

public class PaymentCannotProcessedException extends RuntimeException {
    public PaymentCannotProcessedException(String message) {
        super(message);
    }
}