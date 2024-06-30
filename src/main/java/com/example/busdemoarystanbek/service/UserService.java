package com.example.busdemoarystanbek.service;


import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.model.UserInfo;
import com.example.busdemoarystanbek.repository.*;
import org.hibernate.NonUniqueObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public UserService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.confirmationTokenService = confirmationTokenService;
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User updateUser(UserInfo userInfo, User user) {
        userInfo.setId(user.getId());
        user.setUserInfo(userInfo);
        userRepository.save(user);
        return user;
    }

    public ResponseEntity<?> confirmEmail(String token) {
        var confirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        User user = confirmationToken.orElseThrow().getUser();
        if (user.isEnabled()) return ResponseEntity.ok("email already verified");
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("email successfully verified");
    }

    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new NonUniqueObjectException("", null, user.getEmail());
        userRepository.save(user);
        confirmationTokenService.sendConfirmationToken(user);
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("User with id '%d' not found", id)));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()
                -> new NoSuchElementException(String.format("User with email '%d' not found", email)));
    }
}