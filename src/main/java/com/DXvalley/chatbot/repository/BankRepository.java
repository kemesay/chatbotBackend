package com.DXvalley.chatbot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DXvalley.chatbot.models.Bank;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BankRepository extends JpaRepository<Bank,Long>{
    Bank findByName(String Name);
    Bank findByBankId(Long bankId);
@Query("SELECT b from Bank b WHERE b.destination.name = :destinationName")
    List<Bank> findBanksAtDestination(String destinationName);

//    List<Bank> findByDestinationsIn(List<String> destination);
}
