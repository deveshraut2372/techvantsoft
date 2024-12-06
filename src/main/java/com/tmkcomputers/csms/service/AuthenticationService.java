package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.dto.LoginUserDto;
import com.tmkcomputers.csms.dto.RegisterUserDto;
import com.tmkcomputers.csms.model.Role;
import com.tmkcomputers.csms.model.RoleEnum;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.repository.RoleRepository;
import com.tmkcomputers.csms.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User socialLogin(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }


        User user = userRepository.findByUsername(input.getEmail()).orElse(null);
        if(user != null) {
            return user;
        }

        if(input.getPassword() == null || input.getPassword().trim().length() == 0) {
            input.setPassword("TEMPORARY_PASSWORD");
        }

        user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setMobileNumber(input.getMobileNumber());
        user.setAuthProvider(input.getAuthProvider());
        user.setRoles(Set.of(optionalRole.get()));

        return userRepository.save(user);
    }

    public User signup(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        if(input.getPassword() == null || input.getPassword().trim().length() == 0) {
            input.setPassword("TEMPORARY_PASSWORD");
        }

        User user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setMobileNumber(input.getMobileNumber());
        user.setAuthProvider("MOBILE");
        user.setRoles(Set.of(optionalRole.get()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getEmail())
                .orElseThrow();
    }
}
