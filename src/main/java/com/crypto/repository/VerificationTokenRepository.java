package com.crypto.repository;

import com.crypto.model.VerificationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends TokenRepository<VerificationToken> {
}
