package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.LoginDTO;
import com.jobportal.jobportal.dto.ResponseDTO;
import com.jobportal.jobportal.dto.UserDTO;
import com.jobportal.jobportal.entity.OTPDocument;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.exceptions.JobPortalException;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.utility.EmailTemplate;
import com.jobportal.jobportal.utility.Utilities;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private OTPService otpService;

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

    @Override
    public Boolean sendOtp(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        MimeMessage mm= mailSender.createMimeMessage();
        MimeMessageHelper message= new MimeMessageHelper(mm,true);
        message.setTo(email);
        message.setSubject(EmailTemplate.getSubject());
        //According to Code Marshal Code
        //String otp= Utilities.generateOTP();
        // More Secure Version
        String otp = otpService.generateAndStoreOTP(String.valueOf(user.getId()));
        message.setText(EmailTemplate.generateOtpEmailHtml(user.getName(),otp,5),true);
        mailSender.send(mm);
        return true;
    }

    @Override
    public boolean verifyOTP(String email, String otp) throws JobPortalException {
        boolean isVerified=false;
        User user = userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        OTPDocument otpDocument=otpService.getOTPDetails(String.valueOf(user.getId()));
        if(otpDocument==null)
            throw new JobPortalException("OTP not found");
        if(otpDocument.getExpiresAt()<System.currentTimeMillis())
            throw new JobPortalException("OTP Expired!!! Try login again");
        if(otpDocument.getAttempts()>0)
            isVerified =otpService.verifyOTP(String.valueOf(user.getId()),otp);
        if(!isVerified){
            int attempsLeft= 2-otpDocument.getAttempts();
            throw new JobPortalException("Invalid OTP "+attempsLeft+ " attempts left");
        }
        return isVerified;
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException{
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("Password changed Successfully.");
    }


}
