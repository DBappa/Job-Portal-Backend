package com.jobportal.jobportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "otp_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPDocument {

    @Id
    private String id;
    private String userId;
    private String otpHash;
    private String salt;
    private long expiresAt;
    private long createdAt;
    private int attempts;
}

