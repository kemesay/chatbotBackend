package com.DXvalley.chatbot.messageManager.email;



import com.DXvalley.chatbot.utils.ApiResponse;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    boolean isValidEmail(String email);

    CompletableFuture<ApiResponse> send(String recipientEmail, String emailBody, String emailSubject);
}
