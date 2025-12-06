package com.jobportal.jobportal.service;

public interface OTPService {

    public String generateAndStoreOTP(String userId);
    public boolean verifyOTP(String userId, String userInputOtp);

}
