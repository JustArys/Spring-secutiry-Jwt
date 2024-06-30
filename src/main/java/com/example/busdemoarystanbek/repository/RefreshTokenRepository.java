package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    void deleteById(Long id);
}