package com.DXvalley.chatbot.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;

    // getters and setters
}
