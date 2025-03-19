package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageId;
    private String packageName;
    private String stayDuration;
    private String packagePricePerPerson;
    private String maxGroup;
    private List<String> touristType;
    private List<String> packageForDorInter;
    private String packageDescription;
    private String departureDates;
    private String createdAt;
    private String updatedAt;

    @ManyToOne
    private Users packageCreator;
    @Enumerated(EnumType.STRING)
    private PackageType packageType;
//    @NotNull
    @ManyToMany
    private List<Destination> destinations = new ArrayList<>();

    @ManyToMany
    private Collection<TourOperator> tourOperator = new ArrayList<>();

    public TourPackage(String maxGroup, List<String> packageForDorInter, List<String> touristType, String updatedAt, String packageDescription, String departureDates, String createdAt,
                       String packagePricePerPerson, String stayDuration, String packageName) {
        this.packageName = packageName;
        this.stayDuration = stayDuration;
        this.packagePricePerPerson = packagePricePerPerson;
        this.maxGroup = maxGroup;
        this.packageDescription = packageDescription;
        this.departureDates = departureDates;
        this.createdAt = createdAt;
        this.updatedAt= updatedAt;
        this.touristType = touristType;
        this.packageForDorInter = packageForDorInter;
    }


}
