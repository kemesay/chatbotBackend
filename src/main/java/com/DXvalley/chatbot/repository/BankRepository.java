package com.DXvalley.chatbot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DXvalley.chatbot.models.Bank;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank,Long>{
    Bank findByName(String Name);
    Bank findByBankId(Long bankId);

//    List<Bank> findByDestinationsIn(List<String> destination);
}
