package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TourPackageRepository extends JpaRepository<TourPackage,Long> {

    TourPackage findByPackageName(String packageName);
    TourPackage findByPackageId (Long PackageId);


}
