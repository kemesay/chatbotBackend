package com.DXvalley.chatbot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DXvalley.chatbot.models.Bank;

public interface BankRepository extends JpaRepository<Bank,Long>{
    Bank findByBankNamebranchName(String BankNamebranchName);
    Bank findByBankId(Long bankId);
}
