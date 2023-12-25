package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.TourPackage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TourPackageService {

    ResponseEntity<?> registerPackage(TourPackage tourPackage);

    List<TourPackage> fetchPackages();
    TourPackage editTourPackage (TourPackage tourPackage);
}
