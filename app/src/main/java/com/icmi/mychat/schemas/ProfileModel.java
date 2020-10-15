package com.icmi.mychat.schemas;

public class ProfileModel {

    private String name;
    private String profileUrl;
    private String contact;

    public ProfileModel() {
    }

    public ProfileModel(String name, String profileUrl, String contact) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
