package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.DTO.RateDTO;
import com.DXvalley.chatbot.models.Rate;
import com.DXvalley.chatbot.repository.RateRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.RateService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/rate")
public class RateController {
    @Autowired
    private RateService rateService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RateRepository rateRepository;
    @PostMapping("/push")
    private ResponseEntity<?> rate(@RequestBody Rate rate) {
        Rate user = rateRepository.findByUserId(rate.getUserId());
        createUserResponse response;
        Boolean isRatable;
        Float rateNum = rate.getRate() * 2;
        isRatable = rateNum % 1 == 0 && rateNum >= 1 && rateNum <= 10;
        if (!isRatable) {
            response = new createUserResponse("fail", "Illegal rate value");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
//        if (user != null ) {
        rate.setUsername(rate.getUsername());
        rateService.rate(rate);
            response = new createUserResponse("success", "successfully rated!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
////        }
//        else {
//            response = new createUserResponse("fail", "user does not exist!");
//            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//        }
         }

        @GetMapping("/fetch")
        ResponseEntity<?> getAllRates () {
            RateDTO ratesAnalysis = rateService.fetchAllRates();
            createUserResponse response;
            if (ratesAnalysis.getRate().size() > 0) {
                return new ResponseEntity<>(ratesAnalysis, HttpStatus.OK);
            } else {
                response = new createUserResponse("fail", "not rated yet");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        class ResponseMessage {
            String status;
            String description;
        }
    }
