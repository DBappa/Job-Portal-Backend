package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.OTPDocument;

public interface OTPService {

    public String generateAndStoreOTP(String userId);
    public boolean verifyOTP(String userId, String userInputOtp);
    public OTPDocument getOTPDetails(String userId);

}
