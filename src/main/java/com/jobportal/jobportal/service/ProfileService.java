package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.ProfileDTO;
import com.jobportal.jobportal.exceptions.JobPortalException;

public interface ProfileService {

    public Long createProfile(String email);

    public ProfileDTO getProfile(Long id) throws JobPortalException;

    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
}
