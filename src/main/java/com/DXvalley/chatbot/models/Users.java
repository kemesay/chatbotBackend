package com.DXvalley.chatbot.models;
import java.util.ArrayList;
import java.util.Collection;
import com.DXvalley.chatbot.auth_provider.Provider;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String email;
    private Boolean emailConfirmed;
    private String gender;
    private String birthDate;
    private String description;
    private String imageUrl;
    private String coverImgUrl;
    private String ip;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Integer languageCode;
    private Integer accessFailedCount;
    private Boolean twoFactorEnabled;
    private Boolean isActive = false;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @OneToOne
    private TourOperator tourOperator;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "destination_destination_id")
    private Destination destination;

    //user address
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    // Additional methods...
    public Users(String username,String updatedAt, String description, String phoneNumber, String password, String fullName, String email, Boolean emailConfirmed, String gender, String birthDate, String imageUrl, String coverImgUrl, String ip, String createdAt,
                 String deletedAt, Integer languageCode, Integer accessFailedCount, Boolean twoFactorEnabled, Boolean isActive) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.emailConfirmed = emailConfirmed;
        this.gender = gender;
        this.description = description;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.coverImgUrl = coverImgUrl;
        this.updatedAt =updatedAt;
        this.ip = ip;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.languageCode = languageCode;
        this.accessFailedCount = accessFailedCount;
        this.twoFactorEnabled = twoFactorEnabled;
        this.isActive = isActive;
        //this.verificationCode=verificationCode;
        // this.verificationCodeCreatedAt=verificationCodeCreatedAt;
    }

}
