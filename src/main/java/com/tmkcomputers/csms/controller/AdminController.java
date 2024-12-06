package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.dto.CreateManagerRequest;
import com.tmkcomputers.csms.dto.RegisterUserDto;
import com.tmkcomputers.csms.dto.UserProfileResponse;
import com.tmkcomputers.csms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        UserProfileResponse createdAdmin = userService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

    @PostMapping("/create-tenant-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> createTenantManager(@RequestBody CreateManagerRequest input) {
        UserProfileResponse createdManager = userService.createManager("TENANT", input);
        return ResponseEntity.ok(createdManager);
    }

    @PostMapping("/create-charging-station-manager")
    @PreAuthorize("hasAnyRole('ADMIN', 'TENANT_MANAGER')")
    public ResponseEntity<UserProfileResponse> createChargingStationManager(@RequestBody CreateManagerRequest input) {
        UserProfileResponse createdManager = userService.createManager("CHARGING_STATION", input);
        return ResponseEntity.ok(createdManager);
    }

    @GetMapping("/tenant-managers/{tenantId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserProfileResponse>> getTenantManagers(@PathVariable Long tenantId) {
        List<UserProfileResponse> managers = userService.getAllManagers("TENANT", tenantId);
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/charging-station-managers/{chargingStationId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TENANT_MANAGER')")
    public ResponseEntity<List<UserProfileResponse>> getChargingStationManagers(@PathVariable Long chargingStationId) {
        List<UserProfileResponse> managers = userService.getAllManagers("CHARGING_STATION", chargingStationId);
        return ResponseEntity.ok(managers);
    }
}
