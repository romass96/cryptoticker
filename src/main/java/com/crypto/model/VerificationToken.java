package com.crypto.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken extends Token {

    public VerificationToken(User user, String token) {
        super(user, token);
    }
}
