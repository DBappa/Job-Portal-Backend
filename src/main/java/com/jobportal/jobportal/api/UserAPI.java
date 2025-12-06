package com.jobportal.jobportal.api;


import com.jobportal.jobportal.dto.LoginDTO;
import com.jobportal.jobportal.dto.ResponseDTO;
import com.jobportal.jobportal.dto.UserDTO;
import com.jobportal.jobportal.exceptions.JobPortalException;
import com.jobportal.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Validated
public class UserAPI {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws JobPortalException {
        userDTO=userService.registerUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException {
        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
    }

    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable String email) throws Exception {
        userService.sendOtp(email);
        return new ResponseEntity<>(new ResponseDTO("OTP sent successfully"), HttpStatus.OK);
    }

}
