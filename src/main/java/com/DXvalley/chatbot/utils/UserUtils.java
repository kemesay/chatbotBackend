package com.DXvalley.chatbot.utils;

import com.DXvalley.chatbot.exception.customException.BadRequestException;
import com.DXvalley.chatbot.exception.customException.ResourceNotFoundException;
import com.DXvalley.chatbot.messageManager.sms.SmsService;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserUtils {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  DateTimeFormatter dateTimeFormatter;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;


    public void validateUsername(String email) {
        if (!this.emailService.isValidEmail(email) && !this.smsService.isValidPhoneNumber(email)) {
            throw new BadRequestException("neither a valid email nor a valid phone number.");
        }
    }




    public Users utilGetUserByUserId(Long userId) {
        Users user =  userRepository.findById(userId).orElseThrow(() -> {
            return new ResourceNotFoundException("There is no user with this Id");
        });
        return user;
    }

    public Users utilGetUserByUsername(String email) {
        Users user = userRepository.findByUsername(email);
        System.err.println("user"+ user);
        if (user == null) {
            throw new ResourceNotFoundException("There is no user with this username.");
        }
        return user;
    }

    public Users saveUser(Users user) {

        return (Users) userRepository.save(user);
    }

    public void delete(String email) {
        Users user = utilGetUserByUsername(email);
        userRepository.delete(user);
    }


    public void verifyUserEmail(Users user) {
        if (user.getEmail() == null) {
            throw new BadRequestException("User email is required for future communication.");
        }
    }


}
