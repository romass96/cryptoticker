package com.crypto;

import com.crypto.model.Role;
import com.crypto.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleRepository.findByName(Role.ROLE_USER);
        if (userRole == null) {
            roleRepository.save(new Role(Role.ROLE_USER));
        }

        Role adminRole = roleRepository.findByName(Role.ROLE_ADMIN);
        if (adminRole == null) {
            roleRepository.save(new Role(Role.ROLE_ADMIN));
        }

    }
}
