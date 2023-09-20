package com.DXvalley.chatbot.controllers;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.repository.BankRepository;
import com.DXvalley.chatbot.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private BankService bankService;
    @Autowired
    private BankRepository bankRepository;
    @PostMapping("/registerBank")
    public ResponseEntity<?> createBank(@RequestBody Bank bank) {
        Bank bank1=bankRepository.findByBankNamebranchName(bank.getBankNamebranchName());
        BankController.ResponseMessage responseMessage;
        if (bank1==null) {
            bankService.registerBank(bank);
            responseMessage = new BankController.ResponseMessage("success", "bank created successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else {
            responseMessage = new BankController.ResponseMessage("fail", "Bank's branch already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    class ResponseMessage {
        String status;
        String description;
    }


}

