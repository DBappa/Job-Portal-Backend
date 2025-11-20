package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

}
