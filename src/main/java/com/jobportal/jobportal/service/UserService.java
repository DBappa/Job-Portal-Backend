package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.LoginDTO;
import com.jobportal.jobportal.dto.UserDTO;
import com.jobportal.jobportal.exceptions.JobPortalException;

public interface UserService {

    public UserDTO registerUser(UserDTO user) throws JobPortalException;

    public UserDTO loginUser(LoginDTO login) throws JobPortalException;


}
