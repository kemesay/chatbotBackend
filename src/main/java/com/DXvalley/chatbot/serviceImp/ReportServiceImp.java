package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.DTO.ReportCardsResponse;
import com.DXvalley.chatbot.DTO.SingleReportCardData;
import com.DXvalley.chatbot.repository.DestinationRepository;
import com.DXvalley.chatbot.repository.TourOPRRepository;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.repository.TouristRepository;
import com.DXvalley.chatbot.service.ReportService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    @Override
    public ReportCardsResponse getCardsData() {
        int touristCount = touristRepository.findAll().size();
        int tourOperatorCounter = tourOpRepository.findAll().size();
        int destinationCounter = destinationRepository.findAll().size();
        int packagesCounter = tourPackageRepository.findAll().size();

        ReportCardsResponse response1 = new ReportCardsResponse();
        SingleReportCardData touristCardData=new SingleReportCardData();
        SingleReportCardData packageCardData=new SingleReportCardData();
        SingleReportCardData destinationCardData=new SingleReportCardData();
        SingleReportCardData tourOperatorCardData=new SingleReportCardData();

        touristCardData.setTitle("Tourists");
        touristCardData.setValue(touristCount);
        response1.setTourists(touristCardData);

        packageCardData.setTitle("Package");
        packageCardData.setValue(packagesCounter);
        response1.setDestinations(packageCardData);

        destinationCardData.setTitle("Destination");
        destinationCardData.setValue(destinationCounter);
        response1.setPackages(destinationCardData);

        tourOperatorCardData.setTitle("Tour Operator");
        tourOperatorCardData.setValue(tourOperatorCounter);
        response1.setTourOperators(tourOperatorCardData);
        return response1;
    }
}
