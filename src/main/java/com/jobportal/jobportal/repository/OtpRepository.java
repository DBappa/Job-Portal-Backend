package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.OTPDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends MongoRepository<OTPDocument, String> {

    Optional<OTPDocument> findByUserId(String userId);
    List<OTPDocument> findByCreatedAtBefore(Long expiredAt);
}



