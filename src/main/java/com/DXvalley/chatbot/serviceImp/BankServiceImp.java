package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.repository.BankRepository;
import com.DXvalley.chatbot.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImp implements BankService {
    @Autowired
    BankRepository bankRepository;
    @Override
    public void registerBank(Bank bank) {bankRepository.save(bank);

    }
}