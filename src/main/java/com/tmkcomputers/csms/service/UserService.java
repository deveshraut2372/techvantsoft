package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.dto.CreateManagerRequest;
import com.tmkcomputers.csms.dto.RegisterUserDto;
import com.tmkcomputers.csms.dto.UpdateUserProfileRequest;
import com.tmkcomputers.csms.dto.UserProfileResponse;
import com.tmkcomputers.csms.model.Role;
import com.tmkcomputers.csms.model.RoleEnum;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.model.UserRole;
import com.tmkcomputers.csms.repository.RoleRepository;
import com.tmkcomputers.csms.repository.UserRepository;
import com.tmkcomputers.csms.repository.UserRoleRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }

    public Optional<User> findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    public UserProfileResponse createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setMobileNumber(input.getMobileNumber());
        user.setAuthProvider("PASSWORD");
        user.setRoles(Set.of(optionalRole.get()));

        user = userRepository.save(user);
        return new UserProfileResponse(
                user.getFullName(),
                user.getMobileNumber(),
                user.getUsername());
    }

    public UserProfileResponse createManager(String entityType, CreateManagerRequest input) {
        RoleEnum roleName = entityType.equals("CHARGING_STATION") ? RoleEnum.CHARGING_STATION_MANAGER
                : RoleEnum.TENANT_MANAGER;
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setMobileNumber(input.getMobileNumber());
        user.setAuthProvider("PASSWORD");
        user.setRoles(Set.of(optionalRole.get()));

        user = userRepository.save(user);
        var optionalUserRole = userRoleRepository.findByUser(user);

        if (optionalUserRole.isEmpty()) {
        } else {
            var userRole = optionalUserRole.get();
            userRole.setActive(true);
            userRole.setEntityId(input.getEntityId());
            userRole.setEntityType(entityType);
            userRoleRepository.save(userRole);
        }

        return new UserProfileResponse(
                user.getFullName(),
                user.getMobileNumber(),
                user.getUsername());
    }

    public List<UserProfileResponse> getAllManagers(String entityType, long entityId) {
        return userRoleRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .stream()
                .map(userRole -> {
                    var user = userRole.getUser();
                    return new UserProfileResponse(
                            user.getFullName(),
                            user.getMobileNumber(),
                            user.getUsername());
                })
                .toList();
    }

    public User updateUserProfile(String email, UpdateUserProfileRequest dto) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(dto.getFullName());
        user.setUsername(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());

        return userRepository.save(user);
    }

    public User loadUserByEmail(String email) {
        var user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    public UserRole getUserRole(User user) {
        return userRoleRepository.findByUser(user)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
