package com.crypto.service;

import com.crypto.model.PasswordResetToken;
import com.crypto.model.User;
import com.crypto.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(user, token);
        passwordTokenRepository.save(resetToken);
    }

    public PasswordResetToken.TokenStatus validatePasswordResetToken(Long userId, PasswordResetToken resetToken) {
        if (resetToken == null || resetToken.getUser().getId() != userId) {
            return PasswordResetToken.TokenStatus.WRONG;
        }
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return PasswordResetToken.TokenStatus.EXPIRED;
        }
        return PasswordResetToken.TokenStatus.CORRECT;
    }

    public void authenticateUserForResetPassword(PasswordResetToken resetToken) {
        UserDetails userDetails = resetToken.getUser().toUserDetails();
        Authentication auth = new UsernamePasswordAuthenticationToken(resetToken.getUser(), null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
