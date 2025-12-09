package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.LoginDTO;
import com.jobportal.jobportal.dto.UserDTO;
import com.jobportal.jobportal.exceptions.JobPortalException;
import jakarta.mail.MessagingException;

public interface UserService {

    public UserDTO registerUser(UserDTO user) throws JobPortalException;

    public UserDTO loginUser(LoginDTO login) throws JobPortalException;


    public Boolean sendOtp(String email) throws Exception;

    public boolean verifyOTP(String email, String otp) throws JobPortalException;
}
