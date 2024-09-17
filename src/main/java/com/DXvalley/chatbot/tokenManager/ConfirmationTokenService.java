package com.DXvalley.chatbot.tokenManager;

import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ConfirmationTokenService {
    ConfirmationToken saveConfirmationToken(Users user, String token, int expirationTimeInMinutes);

    ConfirmationToken getToken(String token,String username);

    void sendConfirmationToken(String contact);

    void sendConfirmationToken(Users users);

    ResponseEntity<ApiResponse> confirmToken(String token);

    ConfirmationToken checkTokenExpiration(String token);
}
