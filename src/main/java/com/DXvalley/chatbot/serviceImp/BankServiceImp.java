package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.repository.BankRepository;
import com.DXvalley.chatbot.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImp implements BankService {

    @Autowired
   public BankRepository bankRepository;
    @Override
    public void registerBank(Bank bank) {bankRepository.save(bank);}
    @Override
    public Bank editBank(Bank bank) {
        return this.bankRepository.save(bank);
    }
    @Override
    public List<Bank> fetchBanks() { return bankRepository.findAll();}


    }
