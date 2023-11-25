package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;


import java.util.List;

public interface BankService {
    List<Bank> fetchBanks();
    Bank editBank (Bank bank);
    void registerBank(Bank bank);

}
