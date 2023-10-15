package com.up.up_contact_sync.repository;

import com.up.up_contact_sync.model.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactTokenRepository extends MongoRepository<UserToken, String> {
    Optional<UserToken> findByToken(String token);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);

}
