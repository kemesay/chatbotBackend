package com.DXvalley.chatbot.service;
import com.DXvalley.chatbot.models.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> fetchFeedbacks();

    void giveFeedback(Feedback feedback);

}
