package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.DTO.ResetPassword;
import com.DXvalley.chatbot.messageManager.email.EmailBuilder;
import com.DXvalley.chatbot.messageManager.email.EmailServiceImpl;
import com.DXvalley.chatbot.messageManager.sms.SmsService;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.UserService;
import com.DXvalley.chatbot.tokenManager.ConfirmationToken;
import com.DXvalley.chatbot.tokenManager.ConfirmationTokenRepository;
import com.DXvalley.chatbot.tokenManager.ConfirmationTokenService;
import com.DXvalley.chatbot.utils.ApiResponse;
import com.DXvalley.chatbot.utils.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceIm  implements UserService {

    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserUtils userUtils;

    @Override
    public List<Users> fetchUsers() {
        List<Users> users = new ArrayList<>();
        users.addAll(userRepository.findAll());

        return users;
    }

    @Override
    public ResponseEntity<?> editUsers(Users users) {

        Users users1 = userRepository.findByUserId(users.getUserId());
        ResponseMessage responseMessage;

        if(users1==null){
            responseMessage= new ResponseMessage("Fail", "cannot Found user {userId}");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else {
            userRepository.save(users);

            responseMessage = new ResponseMessage("success", "user Data Update successfully!");
            return  new ResponseEntity<>(responseMessage, HttpStatus.OK);

        }
    }

    @Override
    public ResponseEntity<?> RegisterUsers(Users users) {
        Users users1 = userRepository.findByPhoneNumber(users.getPhoneNumber());
        ResponseMessage responseMessage;
        if(users1==null){
            userRepository.save(users);
            responseMessage = new ResponseMessage("success","User created Successfully");
            return  new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        else {
            responseMessage = new ResponseMessage("fail", "User already exist");
            return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String emailOrPhoneNumber) {
        Users user = userUtils.utilGetUserByUsername(emailOrPhoneNumber);
        
        if (user == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "There is no user with this username.");
        }

        String code;
        if (emailService.isValidEmail(emailOrPhoneNumber)) {
            code = UUID.randomUUID().toString();
            // String link = "http://localhost:3000/reset-password/" + emailOrPhoneNumber + "/" + code;
            String link = "https://ethiosmartride.com/reset-password/" + emailOrPhoneNumber + "/" + code;


            CompletableFuture<ApiResponse> emailResponse = emailService.send(
                    user.getEmail(),
                EmailBuilder.emailBuilderForPasswordReset(user.getFullName(), link),
                "Reset your password"
            );

            confirmationTokenService.saveConfirmationToken(user, code, 30);
            return ApiResponse.success("Please check your email");
        } else {
            code = String.format("%06d", (new Random()).nextInt(999999));
            smsService.sendOtp(emailOrPhoneNumber, code);
            confirmationTokenService.saveConfirmationToken(user, code, 3);
            return ApiResponse.success("Please check your phone");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> resetPassword(ResetPassword resetPassword) {
        ConfirmationToken confirmationToken = confirmationTokenService.checkTokenExpiration(resetPassword.getToken());
        String emailOrPhoneNumber = confirmationToken.getUser().getEmail();
        Users user = userUtils.utilGetUserByUsername(emailOrPhoneNumber);

        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        userUtils.saveUser(user);
        confirmationTokenRepository.delete(confirmationToken);
        return ApiResponse.success("Hooray! Your password has been successfully reset.");
    }


}
