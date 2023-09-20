package com.DXvalley.chatbot.DTO;

import com.DXvalley.chatbot.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
public class UserProfileDTO {
    private String fullName;
    private String email;
    private String imageUrl;
    private String coverImgUrl;
    Collection< Role> roles;
}
