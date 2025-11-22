package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.LoginDTO;
import com.jobportal.jobportal.dto.UserDTO;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.exceptions.JobPortalException;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent()){
            throw new JobPortalException("USER_FOUND");
        }
        userDTO.setId(sequenceGeneratorService.getNextSequence("user"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LoginDTO login) throws JobPortalException {
        User user = userRepository.findByEmail(login.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        if(!passwordEncoder.matches(login.getPassword(),user.getPassword())){
            throw new JobPortalException("INVALID_PASSWORD");
        }
        return user.toDTO();
    }
}
