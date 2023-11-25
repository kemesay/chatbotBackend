package com.DXvalley.chatbot.security.oauth;
import com.DXvalley.chatbot.auth_provider.Provider;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    public void processOAuthPostLogin(String email,  String name) {
        Optional<Users> existUser = userRepository.findByEmail(email);
        Users newUser = new Users();

        if (existUser == null) {
         // Register new Users
            newUser.setEmail(email);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnabled(false);
            newUser.setFullName(name);
            userRepository.save(newUser);

            System.out.println("Created new user: " + email);
        }
        else {
            // Update the users information

            newUser.setFullName(name);
            newUser.setProvider(Provider.GOOGLE);
            userRepository.save(newUser);
        }

    }
    @Override
    public  void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
       CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler = new OAuth2LoginSuccessHandler();
       oAuth2LoginSuccessHandler.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName());
       String email = oAuth2User.getEmail();
       System.out.println("User's  Email:" + email);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
