package com.DXvalley.chatbot.tokenManager;

import com.DXvalley.chatbot.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class ConfirmationTokenController {
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping({"/checkOtpExistence/{username}/{otpCode}"})
    public ResponseEntity<?> getOtpByCode(@PathVariable String otpCode,@PathVariable String username) {
        ConfirmationToken confirmationToken = this.confirmationTokenService.getToken(otpCode,username);

        return ApiResponse.success("valid otp");
    }

    @PostMapping({"/sendConfirmationToken/{contact}"})
    public ResponseEntity<ApiResponse> sendConfirmationToken(@PathVariable String contact) {
        this.confirmationTokenService.sendConfirmationToken(contact);
        return ApiResponse.success("Confirmation token sent successfully");
    }

    @PutMapping({"/confirm"})
    public ResponseEntity<ApiResponse> confirmUser(@RequestParam String token) {
        return this.confirmationTokenService.confirmToken(token);
    }
}
