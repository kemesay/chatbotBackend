package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> fetchFeedbacks();

//    Feeddback editFeedBack (Feedback feedback);
    void giveFeedback(Feedback feedback);

}
