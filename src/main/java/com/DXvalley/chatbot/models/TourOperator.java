package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TourOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tourOperatorId;
    private String tourOrgName;
    private String ownerFullName;
    private String ownerAddress;
    //    private String email;
//    private String phoneNum;
    private List<String> tourCategory;
    private List<String> touristType;
    private String foundAt;
    private String tinNum;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Destination> destinations;

    public TourOperator(String tourOrgName, List<String> touristType, String ownerFullName, String ownerAddress,
                         String foundAt, List<String> tourCategory, String tinNum) {
        this.tourOrgName = tourOrgName;
        this.ownerFullName = ownerFullName;
        this.ownerAddress = ownerAddress;
        this.foundAt = foundAt;
        this.touristType = touristType;
        this.tourCategory = tourCategory;
        this.tinNum = tinNum;
//        this.email = email;
//        this.phoneNum = phoneNum;
    }

}







