package com.icmi.mychat.schemas;

public class PersonModel {

    private String name;
    private String contact;
    private long lastOnline;
    private String id;
    private String imageUrl;


    public PersonModel() {
    }

    public PersonModel(String name, String contact, long lastOnline, String id, String imageUrl) {
        this.name = name;
        this.contact = contact;
        this.lastOnline = lastOnline;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
