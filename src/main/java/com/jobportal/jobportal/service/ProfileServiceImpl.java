package com.jobportal.jobportal.service;


import com.jobportal.jobportal.entity.Profile;
import com.jobportal.jobportal.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Override
    public Long createProfile(String email) {
        Profile profile= new Profile();
        profile.setId(sequenceGeneratorService.getNextSequence("profiles"));
        profile.setEmail(email);
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());
        profile.setAbout("About me");
        profile.setJobTitle("Job Title");
        profile.setCompany("Company");
        profile.setLocation("Location");

        profile = profileRepository.save(profile);
        return profile.getId();
    }
}
