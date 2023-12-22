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
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String address;
    private String gender;

    private String registeredAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Destination destination;

    private String birthDate;
    private Boolean isActive;



    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Office> office = new ArrayList<>();

}
