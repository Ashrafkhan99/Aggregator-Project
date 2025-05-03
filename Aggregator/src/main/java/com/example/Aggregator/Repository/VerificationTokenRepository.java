package com.example.Aggregator.Repository;

import com.example.Aggregator.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUserId(Long userId);

    void deleteByUserId(Long userId);
}