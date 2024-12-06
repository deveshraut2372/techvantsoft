package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.dto.RoleDto;
import com.tmkcomputers.csms.dto.UpdateUserProfileRequest;
import com.tmkcomputers.csms.dto.UpdateUserProfileResponse;
import com.tmkcomputers.csms.dto.UserProfileResponse;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.model.UserRole;
import com.tmkcomputers.csms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        UserProfileResponse userProfileResponse = new UserProfileResponse(currentUser.getFullName(), currentUser.getMobileNumber(), currentUser.getUsername());
        return ResponseEntity.ok(userProfileResponse);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserProfileResponse>> allUsers() {
        List<User> users = userService.allUsers();

        ArrayList<UserProfileResponse> responses = new ArrayList<>();
        for (User user : users) {

            UserProfileResponse userProfileResponse = new UserProfileResponse(user.getFullName(), user.getMobileNumber(), user.getUsername());
            userProfileResponse.setRoles(String.join(",", user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toList())));

            responses.add(userProfileResponse);
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/roles")
    public ResponseEntity<RoleDto> getRoles(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        UserRole userRole = userService.getUserRole(currentUser);
        RoleDto roleDto = new RoleDto();
        roleDto.setEntityId(userRole.getEntityId());
        roleDto.setRoleName("ROLE_"+userRole.getRole().getName().name());
        return ResponseEntity.ok(roleDto);
    }

    @PutMapping("/update-profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TENANT_MANAGER', 'CHARGING_STATION_MANAGER')") // Ensure only authenticated users can update their profile
    public ResponseEntity<UpdateUserProfileResponse> updateProfile(@RequestBody UpdateUserProfileRequest updateUserProfileDTO, 
                                           Principal principal) {
        String email = principal.getName(); // Get the current authenticated user's email from JWT
        User updatedUser = userService.updateUserProfile(email, updateUserProfileDTO);

        UpdateUserProfileResponse updateUserProfileResponse = new UpdateUserProfileResponse();
        updateUserProfileResponse.setSuccess(true);
        updateUserProfileResponse.setMessage("Profile updated successfully");
        updateUserProfileResponse.setUserProfileResponse(new UserProfileResponse(updatedUser.getFullName(), updatedUser.getMobileNumber(), updatedUser.getUsername(), null));

        return ResponseEntity.ok(updateUserProfileResponse);
    }
}
