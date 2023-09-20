package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.DTO.RateDTO;
import com.DXvalley.chatbot.models.Rate;

public interface RateService {
    void rate(Rate rate);
    RateDTO fetchAllRates();


}
