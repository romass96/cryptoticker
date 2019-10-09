package com.crypto.repository;

import com.crypto.model.PasswordResetToken;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends TokenRepository<PasswordResetToken> {
}
