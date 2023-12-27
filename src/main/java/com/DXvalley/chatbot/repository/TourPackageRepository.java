package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {

    TourPackage findByPackageName(String packageName);

    TourPackage findByPackageId(Long PackageId);

    @Query("SELECT COUNT(p) from TourPackage p join p.destinations d WHERE d.name = :destinationName")
    int countPackagesAtDestination(String destinationName);

    @Query("SELECT p FROM TourPackage p JOIN p.destinations d WHERE d.name = :destinationName")
    List<TourPackage> findTourPackagesAtDestination(String destinationName);
}
