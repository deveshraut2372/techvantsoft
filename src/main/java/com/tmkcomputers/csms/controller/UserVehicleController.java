package com.tmkcomputers.csms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.tmkcomputers.csms.dto.CreateUserVehicleDto;
import com.tmkcomputers.csms.model.UserVehicle;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.service.UserVehicleService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-vehicles")
@Validated
public class UserVehicleController {

    private final UserVehicleService service;

    public UserVehicleController(UserVehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserVehicle> getAll(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        return service.findAllByUserId(currentUser.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVehicle> getById(@PathVariable Long id) {
        Optional<UserVehicle> userVehicle = service.findById(id);
        return userVehicle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UserVehicle> create(@Valid @RequestBody CreateUserVehicleDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        UserVehicle savedUserVehicle = service.addUserVehicle(dto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVehicle> update(@PathVariable Long id, @Valid @RequestBody CreateUserVehicleDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<UserVehicle> userVehicle = service.findById(id);
        if (!userVehicle.isPresent()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        if (!userVehicle.get().getUser().getId().equals(currentUser.getId())) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); }
        UserVehicle updatedUserVehicle = service.updateUserVehicle(dto, userVehicle.get());
        return ResponseEntity.ok(updatedUserVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<UserVehicle> userVehicle = service.findById(id);
        if (!userVehicle.isPresent()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        if (!userVehicle.get().getUser().getId().equals(currentUser.getId())) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
