package com.icmi.mychat.schemas;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class ProfileModel {

    private String name;
    private String profileUrl;
    private String contact;
    private ArrayList<ChatHistoryModel> chats;
    private String id;

    public ProfileModel() {
    }

    public ProfileModel(String name, String profileUrl, String contact, ArrayList<ChatHistoryModel> chats) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.contact = contact;
        this.chats = chats;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<ChatHistoryModel> getChats() {
        return chats;
    }

    public void setChats(ArrayList<ChatHistoryModel> chats) {
        this.chats = chats;
    }
}
