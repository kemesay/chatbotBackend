package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.TourPackage;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PackageServiceImp implements TourPackageService{

    @Autowired
    TourPackageRepository tourPackageRepository;
    @Override
    public void registerPackage(TourPackage tourPackage) {
        tourPackageRepository.save(tourPackage);
    }


}

