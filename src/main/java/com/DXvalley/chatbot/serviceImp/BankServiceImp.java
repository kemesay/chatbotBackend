package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.BankRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImp implements BankService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public BankRepository bankRepository;

    @Override
    public ResponseEntity<?> registerBank(Bank bank) {
        Bank bank1 = bankRepository.findByName(bank.getName());
        ResponseMessage responseMessage;
        if (bank1 == null) {
            bank.setDestination(getUser().getDestination());
            bankRepository.save(bank);
            responseMessage = new ResponseMessage("success", "bank created successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new ResponseMessage("fail", "Bank's branch already exist");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Bank editBank(Bank bank) {
        return this.bankRepository.save(bank);
    }

    @Override
    public List<Bank> fetchBanks() {
        Users user = getUser();
        List<Bank> banks = new ArrayList<>();
        for (Role userRole :
                user.getRoles()) {
            String role = userRole.getRoleName();
            if (role.equals("System Admin")) {
                banks.addAll(bankRepository.findAll());
            } else if (role.equals("admin")) {
                String destinationName = user.getDestination().getName();
                banks.addAll(bankRepository.findBanksAtDestination(destinationName));
            } else {

            }
        }
        return banks;
    }
    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}
@Getter
@Setter
@AllArgsConstructor
class ResponseMessage {
    String status;
    String description;
}