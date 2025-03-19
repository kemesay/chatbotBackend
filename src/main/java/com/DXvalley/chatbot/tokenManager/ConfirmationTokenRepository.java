package com.DXvalley.chatbot.tokenManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    Optional<ConfirmationToken> findByTokenAndUserUsername(String token,String email);

    @Query("SELECT c FROM ConfirmationToken c WHERE c.user.email = :phoneNumber")
    ConfirmationToken findOtpByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM ConfirmationToken c WHERE c.user.email = :phoneNumber AND c.token =:token")
    ConfirmationToken findOtpByPhoneNumberAndByCode(String phoneNumber, String token);

}
