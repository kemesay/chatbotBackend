package com.DXvalley.chatbot.security.Gmailsecurity;


import com.DXvalley.chatbot.auth_provider.Provider;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String username) {
        Users existUser = repo.findByUsername(username);

        if (existUser == null) {
            Users newUser = new Users();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnabled(true);

            repo.save(newUser);

            System.out.println("Created new user: " + username);
        }

    }

}
