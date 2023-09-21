package com.DXvalley.chatbot.serviceImp;

import com.DXvalley.chatbot.models.TourPackage;
import com.DXvalley.chatbot.repository.TourPackageRepository;
import com.DXvalley.chatbot.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PackageServiceImp implements TourPackageService{

    @Autowired
    TourPackageRepository tourPackageRepository;
    @Override
    public void registerPackage(TourPackage tourPackage) {
        tourPackageRepository.save(tourPackage);
    }

    @Override
    public TourPackage editTourPackage(TourPackage tourPackage) {
        return this.tourPackageRepository.save(tourPackage);
    }
    @Override
    public List<TourPackage> fetchPackages() { return tourPackageRepository.findAll();}


}

