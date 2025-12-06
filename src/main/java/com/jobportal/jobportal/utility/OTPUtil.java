package com.jobportal.jobportal.utility;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class OTPUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final char[] ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String generateOTP(int length) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = ALPHANUM[SECURE_RANDOM.nextInt(ALPHANUM.length)];
        }
        return new String(chars);
    }

    public static String generateSalt(int size) {
        byte[] bytes = new byte[size];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String hashOTP(String otp, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest((otp + salt).getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash OTP", e);
        }
    }
}

