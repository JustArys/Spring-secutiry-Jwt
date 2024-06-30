package com.example.busdemoarystanbek.service;


import com.example.busdemoarystanbek.model.ConfirmationToken;
import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.repository.ConfirmationTokenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    public ConfirmationToken saveConfirmationToken(User user) {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(user)
                .confirmationToken(UUID.randomUUID().toString())
                .expirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
                .build();
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    public ResponseEntity<?> sendConfirmationToken(User user) {
        ConfirmationToken confirmationToken = saveConfirmationToken(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/v1/user/confirmemail?token=" + confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);
        return ResponseEntity.ok("confirmation token sent");
    }


}