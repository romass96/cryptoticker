package com.crypto.service;

import com.crypto.model.PasswordResetToken;
import com.crypto.model.User;
import com.crypto.repository.PasswordTokenRepository;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->new UsernameNotFoundException(email));

        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->new UsernameNotFoundException(email));
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(user, token);
        passwordTokenRepository.save(resetToken);
    }

    public PasswordResetToken.TokenStatus validatePasswordResetToken(Long userId, String token) {
        PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getUser().getId() != userId) {
            return PasswordResetToken.TokenStatus.WRONG;
        }

        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return PasswordResetToken.TokenStatus.EXPIRED;
        }
        return PasswordResetToken.TokenStatus.CORRECT;
    }
}
