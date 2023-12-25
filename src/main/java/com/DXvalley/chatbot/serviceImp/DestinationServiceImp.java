package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImp implements DestinationService {
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    @Override
    public Destination editDestination(Destination destination) {
        return this.destinationRepository.save(destination);
    }

    @Override
    public List<Destination> fetchDestinations() {
        Users user = getUser();
        int i=0;
        List<Destination> destinations = destinationRepository.findAll();
        List<Destination> destinationToReturn = new ArrayList<>();
        for (Role role :
                user.getRoles()) {
            if (role.getRoleName().equals("Tour Operator")) {
                for (Destination destination :
                        destinations) {
                    for (Destination destToFilter :
                            user.getTourOperator().getDestinations()) {
                        System.out.println(destToFilter.getName() + " vs " + destination.getName());
                        if (destToFilter.getName().equals(destination.getName())) {
                            i++;
                            destinationToReturn.add(destination);
                        }
                    }
                }
            } else return destinations;
        }
        System.out.println(i);
        return destinationToReturn;
    }

    public List<Destination> fetchDestinations2() {
        Users user = getUser();

        List<Destination> destinations = destinationRepository.findAll();

        return user.getRoles().stream()
                .filter(role -> "Tour Operator".equals(role.getRoleName()))
                .flatMap(role -> destinations.stream()
                        .filter(destination -> user.getDestination().getName().equals(destination.getName())))
                .collect(Collectors.toList());
    }

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }
}


