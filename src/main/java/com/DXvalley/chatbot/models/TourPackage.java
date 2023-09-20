package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class TourPackage {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long packageId;
    private String packageName;
    private String stayDuration;
    private String packagePricePerPerson;
    private String maxGroup;
    private String packageDescription;
    private String departureDates;
    private String createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Destination> destinations = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<TourOpertaor> tourOpertaor = new ArrayList<>();



    public TourPackage(String maxGroup,  String packageDescription  ,String departureDates,  String createdAt,
                        String packagePricePerPerson, String stayDuration,  String packageName){
        this.packageName=packageName;
        this.stayDuration=stayDuration;
        this.packagePricePerPerson=packagePricePerPerson;
        this.maxGroup=maxGroup;
        this.packageDescription=packageDescription;
        this.departureDates=departureDates;
        this.createdAt= createdAt;
    }


}
