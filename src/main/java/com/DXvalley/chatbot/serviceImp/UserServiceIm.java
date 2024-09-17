package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.DTO.ResetPassword;
import com.DXvalley.chatbot.messageManager.sms.SmsService;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.EmailService;
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

@Service
public class UserServiceIm  implements UserService {

    @Autowired
    private EmailService emailService;
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
    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String username) {
        Users user = userUtils.utilGetUserByUsername(username);
        System.err.println("user2"+ user);

        String code;
        if (emailService.isValidEmail(username)) {
            code = UUID.randomUUID().toString();
//            String link = "https://chatoromia.org/reset-password/" + username + "/" + code;
            String link = "http://localhost:3000/reset-password/" + username + "/" + code;

//            emailService.send(user.getUsername(), EmailBuilder.emailBuilderForPasswordReset(user.getFullName(), link), "Reset your password");
            confirmationTokenService.saveConfirmationToken(user, code, 30);
            return ApiResponse.success("Please check your email");
        } else {
            code = String.format("%06d", (new Random()).nextInt(999999));
            smsService.sendOtp(username, code);
            confirmationTokenService.saveConfirmationToken(user, code, 3);
            return ApiResponse.success("Please check your phone");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> resetPassword(ResetPassword resetPassword) {
        ConfirmationToken confirmationToken = confirmationTokenService.checkTokenExpiration(resetPassword.getToken());
        String username = confirmationToken.getUser().getUsername();
        Users user = userUtils.utilGetUserByUsername(username);

        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        userUtils.saveUser(user);
        confirmationTokenRepository.delete(confirmationToken);
        return ApiResponse.success("Hooray! Your password has been successfully reset.");
    }


}
