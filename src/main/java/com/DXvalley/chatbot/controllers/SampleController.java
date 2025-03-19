package com.DXvalley.chatbot.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sample APIs.")
@RequestMapping("/")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class SampleController {
    @GetMapping
    public Map<String, Object>  currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return  oAuth2AuthenticationToken.getPrincipal().getAttributes();

    }
}
