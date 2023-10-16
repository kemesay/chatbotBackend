package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.DTO.ReportCardsResponse;
import com.DXvalley.chatbot.DTO.SingleReportCardData;
import com.DXvalley.chatbot.repository.*;
import com.DXvalley.chatbot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeRepository employeeRepository;

    @Override
    public ReportCardsResponse getCardsData() {
        int touristCount = touristRepository.findAll().size();
        int tourOperatorCounter = tourOpRepository.findAll().size();
        int destinationCounter = destinationRepository.findAll().size();
        int packagesCounter = tourPackageRepository.findAll().size();
        int employeeCounter = employeeRepository.findAll().size();

        ReportCardsResponse response1 = new ReportCardsResponse();
        SingleReportCardData touristCardData=new SingleReportCardData();
        SingleReportCardData packageCardData=new SingleReportCardData();
        SingleReportCardData destinationCardData=new SingleReportCardData();
        SingleReportCardData tourOperatorCardData=new SingleReportCardData();
        SingleReportCardData employeeCardData=new SingleReportCardData();




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
