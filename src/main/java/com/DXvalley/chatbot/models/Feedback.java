package com.DXvalley.chatbot.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long feedbackId;

    private String first_name;
    private String last_name;
    private String email;
    private String feedbackText;
    private String sentAt;



}
