package com.crypto.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken extends Token {

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(User user, String token) {
        super(user, token);
    }


}
