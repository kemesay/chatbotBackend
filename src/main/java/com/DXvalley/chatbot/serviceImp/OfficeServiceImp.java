package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Office;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.OfficeRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeServiceImp implements OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerOffice(Office office) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        office.setDestination(user.getDestination());
        officeRepository.save(office);
    }

    @Override
    public Office editOffice(Office office) {
        return this.officeRepository.save(office);
    }

    @Override
    public List<Office> fetchOffices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        List<Office> offices = new ArrayList<>();
        for (Role role :
                user.getRoles()) {
            if (role.getRoleName().equals("System Admin")) {
                offices.addAll(officeRepository.findAll());
            } else if (role.getRoleName().equals("admin")) {
                offices.addAll(officeRepository.findDestinationOffices(user.getDestination()));
            }

        }
        return offices;
    }
}
