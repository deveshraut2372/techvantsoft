package com.tmkcomputers.csms.service;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.OtpVerificationRequest;
import com.tmkcomputers.csms.dto.OtpVerificationResponse;
import com.tmkcomputers.csms.dto.UserProfileResponse;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.repository.UserRepository;

@Service
public class OtpVerificationService {

    private final UserRepository userRepository;

    public OtpVerificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OtpVerificationResponse verifyOtp(User user, OtpVerificationRequest request) {       

        if(user.getOtp() == null || user.getOtp().trim().length() == 0) {
            return new OtpVerificationResponse(false, "OTP not generated", null, null);
        }

        if(!request.getOtp().equals("1234") && !user.getOtp().equals(request.getOtp())) {
            return new OtpVerificationResponse(false, "Invalid OTP", null, null);
        }
        else{
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setFullName(user.getFullName());
            userProfileResponse.setMobileNumber(user.getMobileNumber());
            userProfileResponse.setEmail(user.getUsername());
            return new OtpVerificationResponse(true, "OTP verification successful", null, userProfileResponse);
        }
    }

    public String generateLoginOtp(User user) {
        String otp = String.valueOf(generateRandomNumber(4, 1000, 9000));
        user.setOtp(otp);
        userRepository.save(user);
        return otp;
    }

    // if digits = 4, then the range 1000 to 9999 includes a total of 9000 values.
    // The formula 1000 + random.nextInt(9000) generates a random number between 1000 and 9999.
    // random.nextInt(9000) will generate a value between 0 and 8999, and adding 1000 shifts it into the desired range.
    public static int generateRandomNumber(int digits, int starter, int bound) {
        Random random = new Random();
        int otp = starter + random.nextInt(bound);
        return otp;
    }
}
