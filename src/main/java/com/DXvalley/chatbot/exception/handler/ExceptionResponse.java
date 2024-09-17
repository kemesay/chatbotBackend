package com.DXvalley.chatbot.exception.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private final String timeStamp;
    private final HttpStatus error;
    private final String message;
    private final String requestPath;
}
