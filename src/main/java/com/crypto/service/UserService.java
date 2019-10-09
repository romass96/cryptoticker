package com.crypto.service;

import com.crypto.dto.UserDTO;
import com.crypto.model.Role;
import com.crypto.model.User;
import com.crypto.repository.RoleRepository;
import com.crypto.repository.UserRepository;
import com.crypto.util.Utils;
import com.crypto.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserValidator userValidator;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        return user.toUserDetails();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->new UsernameNotFoundException(email));
    }

    public void updateUserPassword(User user, String originalPassword) {
        user.setPassword(passwordEncoder.encode(originalPassword));
        userRepository.save(user);
    }

    public User createUserAccount(UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return null;
        }
        User user = userDTO.toUser();
        Role role = roleRepository.findByName(Role.ROLE_USER);
        role.addUser(user);
        user.setRoles(Utils.asSet(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void setUserEnabled(Long userId, boolean enabled) {
        userRepository.updateUserEnabled(userId, enabled);
    }

    public boolean isUserWithEmailExists(String email) {
        try {
            findUserByEmail(email);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

}
