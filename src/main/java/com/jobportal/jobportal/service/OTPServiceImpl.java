package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.OTPDocument;
import com.jobportal.jobportal.repository.OtpRepository;
import com.jobportal.jobportal.utility.OTPUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service(value="otpService")
public class OTPServiceImpl implements OTPService{


    private final OtpRepository otpRepo;

    public OTPServiceImpl(OtpRepository otpRepo) {
        this.otpRepo = otpRepo;
    }

    /**
     * Creates OTP, stores hashed OTP in DB, returns raw OTP for sending to user.
     */
    public String generateAndStoreOTP(String userId) {

        String otp = OTPUtil.generateOTP(6); // RAW OTP
        String salt = OTPUtil.generateSalt(16);
        String otpHash = OTPUtil.hashOTP(otp, salt);

        long expiry = Instant.now().plusSeconds(300).toEpochMilli(); // 5 min

        // If OTP already exists for user, overwrite it
        OTPDocument doc = otpRepo.findByUserId(userId).orElse(new OTPDocument());
        doc.setUserId(userId);
        doc.setOtpHash(otpHash);
        doc.setSalt(salt);
        doc.setExpiresAt(expiry);
        doc.setCreatedAt(System.currentTimeMillis());
        doc.setAttempts(0);

        otpRepo.save(doc);

        return otp; // SEND THIS VIA EMAIL/SMS
    }

    public boolean verifyOTP(String userId, String userInputOtp) {

        OTPDocument doc = otpRepo.findByUserId(userId)
                .orElse(null);

        if (doc == null) return false;

        // Expiry check
        if (System.currentTimeMillis() > doc.getExpiresAt()) {
            otpRepo.delete(doc);
            return false;
        }

        // Too many attempts
        if (doc.getAttempts() >= 3) {
            otpRepo.delete(doc);
            return false;
        }

        String computedHash = OTPUtil.hashOTP(userInputOtp, doc.getSalt());

        if (computedHash.equals(doc.getOtpHash())) {
            otpRepo.delete(doc); // OTP successfully used and invalidated
            return true;
        } else {
            doc.setAttempts(doc.getAttempts() + 1);
            otpRepo.save(doc);
            return false;
        }
    }

    @Override
    public OTPDocument getOTPDetails(String userId) {
        OTPDocument doc = otpRepo.findByUserId(userId)
                .orElse(null);

        return doc;
    }
}


