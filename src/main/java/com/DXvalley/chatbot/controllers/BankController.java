package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.models.Bank;

import com.DXvalley.chatbot.repository.BankRepository;
import com.DXvalley.chatbot.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private BankService bankService;
    @Autowired
    private BankRepository bankRepository;

    @PostMapping("/registerBank")
    public ResponseEntity<?> createBank(@RequestBody Bank bank) {
      return  bankService.registerBank(bank);

    }

    @GetMapping("/getBanks")
    private ResponseEntity<?> fetchBanks() {
        List<Bank> bank = bankService.fetchBanks();
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }


    @GetMapping("/getBank/{bankId}")
    public ResponseEntity<?> getByBankId(@PathVariable Long bankId) {
        var bank = bankRepository.findByBankId(bankId);
        if (bank == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this bank!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @PutMapping("/edit/{bankId}")
    Bank editBank(@RequestBody Bank bank, @PathVariable Long bankId) {
        Bank bank1 = this.bankRepository.findByBankId(bankId);
        bank1.setName(bank.getName());
        bank1.setAddress(bank.getAddress());
        bank1.setDescription(bank.getDescription());
        bank1.setLatitude(bank.getLatitude());
        bank1.setLongitude(bank.getLongitude());
        return bankService.editBank(bank1);
    }

    @DeleteMapping("/delete/bank/{bankId}")
    void deleteBank(@PathVariable Long bankId) {
        this.bankRepository.deleteById(bankId);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }

}

