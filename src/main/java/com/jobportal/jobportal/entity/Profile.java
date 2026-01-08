package com.jobportal.jobportal.entity;

import java.util.List;

public class Profile {

    private Long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certification> certifications;
}
