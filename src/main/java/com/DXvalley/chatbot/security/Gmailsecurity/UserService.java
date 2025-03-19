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

    public void processOAuthPostLogin(String email) {
        Users existUser = repo.findByEmail(email);

        if (existUser == null) {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsActive(true);

            repo.save(newUser);

            System.out.println("Created new user: " + email);
        }

    }

}
