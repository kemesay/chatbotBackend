package com.DXvalley.chatbot.messageManager.newsLetterSubscriber;

import java.util.List;

public interface NewsLetterSubscriberService {
    List<NewsLetterSubscriber> getAllSubscribers();

    NewsLetterSubscriber subscribe(String email);

    NewsLetterSubscriber getSubscriberByEmail(String email);
}
