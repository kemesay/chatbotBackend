package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.DTO.ResetPassword;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<Users> fetchUsers();
    ResponseEntity<?> editUsers (Users users);
    ResponseEntity<?> RegisterUsers(Users users);

    ResponseEntity<ApiResponse> forgotPassword(String email);
    ResponseEntity<ApiResponse> resetPassword(ResetPassword resetPassword);

}
