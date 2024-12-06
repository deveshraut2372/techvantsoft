package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.dto.LoginResponse;
import com.tmkcomputers.csms.dto.LoginUserDto;
import com.tmkcomputers.csms.dto.OtpVerificationRequest;
import com.tmkcomputers.csms.dto.OtpVerificationResponse;
import com.tmkcomputers.csms.dto.RegisterResponseDto;
import com.tmkcomputers.csms.dto.RegisterUserDto;
import com.tmkcomputers.csms.dto.TokenRefreshRequest;
import com.tmkcomputers.csms.dto.CheckUserExistsRequest;
import com.tmkcomputers.csms.dto.CheckUserExistsResponse;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.service.AuthenticationService;
import com.tmkcomputers.csms.service.JwtService;
import com.tmkcomputers.csms.service.UserService;
import com.tmkcomputers.csms.service.OtpVerificationService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final UserService userService;
    private final OtpVerificationService otpVerificationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService, UserService userService, OtpVerificationService otpVerificationService) {
        this.otpVerificationService = otpVerificationService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        if (registeredUser == null) {
            RegisterResponseDto response = new RegisterResponseDto(false, "Signup failed. Please try again.");
            return ResponseEntity.ok(response);
        }
        generateAndSendOtp(registeredUser);
        RegisterResponseDto response = new RegisterResponseDto(true, "Signup successful. Please verify your account.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        // Authenticate user
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        LoginResponse loginResponse = buildLoginResponse(authenticatedUser);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/social-login")
    public ResponseEntity<LoginResponse> socialLogin(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.socialLogin(registerUserDto);
        if (registeredUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        LoginResponse loginResponse = buildLoginResponse(registeredUser);

        return ResponseEntity.ok(loginResponse);
    }

    private LoginResponse buildLoginResponse(User authenticatedUser) {
        // Generate access token
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Generate refresh token
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        // Create the login response object and set both tokens
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken); // Add refresh token
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return loginResponse;
    }

    // Refresh token endpoint
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAuthToken(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        String email = jwtService.extractEmailFromRefreshToken(refreshToken);
        User userDetails = userService.loadUserByEmail(email);

        // Validate the refresh token
        if (jwtService.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.ok(buildLoginResponse(userDetails));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }

    @PostMapping("/check-user-exists")
    public ResponseEntity<CheckUserExistsResponse> checkUserExists(@RequestBody CheckUserExistsRequest requestDTO) {
        String mobileNumber = requestDTO.getMobileNumber();

        Optional<User> user = userService.findByMobileNumber(mobileNumber);

        if(user.isEmpty()) {
            return ResponseEntity.ok(new CheckUserExistsResponse(true, false, "User does not exist."));
        }
        
        generateAndSendOtp(user.get());

        return ResponseEntity.ok(new CheckUserExistsResponse(true, true, "User exists."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(@RequestBody OtpVerificationRequest request) {
        OtpVerificationResponse response;

        Optional<User> user = userService.findByMobileNumber(request.getMobileNumber());

        if (user.isEmpty()) {
            response = new OtpVerificationResponse(false, "User not found", null, null);
        }

        if(request.getOtp() == null || request.getOtp().trim().length() == 0) {
            response = new OtpVerificationResponse(false, "OTP cannot be empty", null, null);
        }

        response = otpVerificationService.verifyOtp(user.get(), request);

        // Return 200 OK if the OTP is verified, otherwise 400 Bad Request
        if (response.isSuccess()) {
            response.setLoginResponse(buildLoginResponse(user.get()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    private boolean generateAndSendOtp(User user) {
        String generatedOtp = otpVerificationService.generateLoginOtp(user);
        if(generatedOtp == null || generatedOtp.trim().length() == 0) {
            return false;
        }

        // TODO: Send the OTP to the user's mobile number

        return true;
    }
}
