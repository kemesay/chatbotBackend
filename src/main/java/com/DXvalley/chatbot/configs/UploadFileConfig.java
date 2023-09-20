package com.DXvalley.chatbot.configs;


import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UploadFileConfig {
    private String cloud_name = "denkdg1tl";
    private String api_key = "783191128755548";
    private String api_secret = "gOfNeuB2Brj7OA2VZjq7h8pm9YU";

    @Bean
    public Cloudinary cloudinaryConfig() {
        try {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", cloud_name);
            config.put("api_key", api_key);
            config.put("api_secret", api_secret);
            return new Cloudinary(config);
        } catch (Exception ex) {
            throw new IllegalStateException("Error creating Cloudinary bean.", ex);
        }
    }
}