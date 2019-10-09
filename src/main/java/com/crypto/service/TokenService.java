package com.crypto.service;

import com.crypto.model.PasswordResetToken;
import com.crypto.model.Token;
import com.crypto.model.User;
import com.crypto.model.VerificationToken;
import com.crypto.repository.PasswordResetTokenRepository;
import com.crypto.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public PasswordResetToken findResetTokenByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public VerificationToken findVerificationTokenByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public PasswordResetToken createPasswordResetTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(user, token);
        return passwordResetTokenRepository.save(resetToken);
    }

    public VerificationToken createVerificationTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        return verificationTokenRepository.save(verificationToken);
    }

    public Token.TokenStatus validateToken(Long userId, Token token) {
        if (token == null || token.getUser().getId() != userId) {
            return Token.TokenStatus.WRONG;
        }
        Calendar calendar = Calendar.getInstance();
        if (token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return Token.TokenStatus.EXPIRED;
        }
        return Token.TokenStatus.CORRECT;
    }

    public void authenticateUserForResetPassword(PasswordResetToken resetToken) {
        UserDetails userDetails = resetToken.getUser().toUserDetails();
        Authentication auth = new UsernamePasswordAuthenticationToken(resetToken.getUser(), null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
