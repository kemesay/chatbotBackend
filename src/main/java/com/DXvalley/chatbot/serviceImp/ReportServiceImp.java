package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.DTO.ReportCardsResponse;
import com.DXvalley.chatbot.DTO.SingleReportCardData;
import com.DXvalley.chatbot.models.Destination;
import com.DXvalley.chatbot.models.Role;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.*;
import com.DXvalley.chatbot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    private TouristRepository touristRepository;
    @Autowired
    private TourPackageRepository tourPackageRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private TourOPRRepository tourOpRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportCardsResponse getCardsData() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userRepository.findByEmailOrUsername(username, username);
        int touristCount = 0;
        int tourOperatorCounter = 0;
        int destinationCounter = 0;
        int packagesCounter = 0;
        int employeeCounter = 0;


        for (Role userRole :
                user.getRoles()) {
            String role = userRole.getRoleName();
            if (role.equals("System Admin")) {
                touristCount = touristRepository.findAll().size();
                tourOperatorCounter = tourOpRepository.findAll().size();
                destinationCounter = destinationRepository.findAll().size();
                packagesCounter = tourPackageRepository.findAll().size();
                employeeCounter = employeeRepository.findAll().size();
            } else if (role.equals("admin")) {
                Destination destination = user.getDestination();
                touristCount = touristRepository.countTouristsAtDestination(destination);
                employeeCounter = employeeRepository.countEmployeesAtDestination(destination.getDestinationId());
                tourOperatorCounter = tourOpRepository.countTourOperatorsAtDestination(destination);
                destinationCounter = destinationRepository.findAll().size();
                packagesCounter = tourPackageRepository.findAll().size();
            } else {

            }
        }


        ReportCardsResponse response1 = new ReportCardsResponse();
        SingleReportCardData touristCardData = new SingleReportCardData();
        SingleReportCardData packageCardData = new SingleReportCardData();
        SingleReportCardData destinationCardData = new SingleReportCardData();
        SingleReportCardData tourOperatorCardData = new SingleReportCardData();
        SingleReportCardData employeeCardData = new SingleReportCardData();


        touristCardData.setTitle("Tourists");
        touristCardData.setValue(touristCount);
        response1.setTourists(touristCardData);

        packageCardData.setTitle("Package");
        packageCardData.setValue(packagesCounter);
        response1.setPackages(packageCardData);

        destinationCardData.setTitle("Destination");
        destinationCardData.setValue(destinationCounter);
        response1.setDestinations(destinationCardData);

        tourOperatorCardData.setTitle("Tour Operator");
        tourOperatorCardData.setValue(tourOperatorCounter);
        response1.setTourOperators(tourOperatorCardData);

        employeeCardData.setTitle(" Employee");
        employeeCardData.setValue(employeeCounter);
        response1.setEmployee(employeeCardData);
        return response1;
    }
}
