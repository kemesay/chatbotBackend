package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    Feedback findByFeedbackId(Long feedbackId);

}
