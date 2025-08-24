package com.emp.repository;

import com.emp.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
}
