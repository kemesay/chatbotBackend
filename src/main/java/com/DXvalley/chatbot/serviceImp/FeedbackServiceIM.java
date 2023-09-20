package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Feedback;
import com.DXvalley.chatbot.repository.FeedbackRepository;
import com.DXvalley.chatbot.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceIM  implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> fetchFeedbacks() {
        return feedbackRepository.findAll();
    }
    @Override
    public void giveFeedback(Feedback feedback) {feedbackRepository.save(feedback);
    }
}

