package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String confirmationToken);
    LinkedList<ConfirmationToken> findAllByExpirationDateBefore(Date date);

}