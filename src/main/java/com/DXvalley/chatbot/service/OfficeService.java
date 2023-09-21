package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Office;

import java.util.List;

public interface OfficeService {
    void registerOffice(Office office);
    List<Office> fetchOffices();
    Office editOffice (Office office);
}
