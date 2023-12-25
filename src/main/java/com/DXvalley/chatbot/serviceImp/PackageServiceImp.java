package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.controllers.tourOpController;
import com.DXvalley.chatbot.models.*;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PackageServiceImp implements TourPackageService {

    @Autowired
    TourPackageRepository tourPackageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> registerPackage(TourPackage tourPackage) {
        var package1 = tourPackageRepository.findByPackageName(tourPackage.getPackageName());
        tourOpController.ResponseMessage responseMessage;
        if (package1 == null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tourPackage.setCreatedAt(dateFormat.format(date));
            Users user = getUser();
            List<Destination> destinations = new ArrayList<>();
            for (Role role :
                    user.getRoles()) {
                String userRole = role.getRoleName();
                boolean isTourOperator = userRole.equals("Tour Operator");
                boolean isAdmin = userRole.equals("admin");
                boolean isSuperAdmin = userRole.equals("System Admin");
                if (isTourOperator) {
                    tourPackage.setPackageType(PackageType.TOUR_OPERATOR_PACKAGE);
                } else if (isAdmin || isSuperAdmin) {
                    tourPackage.setPackageType(PackageType.DESTINATION_PACKAGE);
                    if (isAdmin) {
                        destinations.add(user.getDestination());
                        tourPackage.setDestinations(destinations);
                    }
                }
            }
            tourPackage.setPackageCreator(user);
            tourPackageRepository.save(tourPackage);
            responseMessage = new tourOpController.ResponseMessage("success", "Package Registered successfully");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new tourOpController.ResponseMessage("fail", "Package register fail");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public TourPackage editTourPackage(TourPackage tourPackage) {
        return this.tourPackageRepository.save(tourPackage);
    }

    @Override
    public List<TourPackage> fetchPackages() {
        return tourPackageRepository.findAll();
    }

  public  Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        return user;
    }

}

