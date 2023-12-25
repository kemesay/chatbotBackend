package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.controllers.tourOpController;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.TourOperator;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.RoleRepository;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.TourOpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TourOpServiveImp implements TourOpService {
    @Autowired
    private TourOPRRepository tourOPRRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> registerTourOrg(Users tempUser) {

        Users user =getUser();
        List<Destination> destinations = new ArrayList<>();


        var tourOp1 = tourOPRRepository.findByTinNum(tempUser.getTourOperator().getTinNum());
        tourOpController.ResponseMessage responseMessage;
        if (tourOp1 == null) {
            Role role = roleRepository.findByRoleName("Tour Operator");
            List<Role> roles = new ArrayList<>();
            TourOperator tourOperator=tempUser.getTourOperator();
            roles.add(role);
            destinations.add(user.getDestination());
            tourOperator.setDestinations(destinations);
            tourOPRRepository.save(tourOperator);

            tempUser.setTourOperator(tourOperator);
            tempUser.setPassword(new BCryptPasswordEncoder().encode(tempUser.getPassword()));
            tempUser.setRoles(roles);
            tempUser.setIsEnabled(true);
            userRepository.save(tempUser);
            responseMessage = new tourOpController.ResponseMessage("success", "TourOperator Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new tourOpController.ResponseMessage("fail", "TourOperator register fail");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public TourOperator editTourOp(TourOperator tourOperator) {
        return this.tourOPRRepository.save(tourOperator);
    }

    @Override
    public List<TourOperator> fetchTourOperators() {
        return tourOPRRepository.findAll();
    }

    public  Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}