package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface BankService {
    List<Bank> fetchBanks();
    Bank editBank (Bank bank);
    ResponseEntity<?> registerBank(Bank bank);

}
