package com.icmi.mychat.schemas;

public class ChatHistoryModel {

    private String friendId;
    private String hisProfileImage;
    private String chatReference;

    public ChatHistoryModel() {
    }

    public ChatHistoryModel(String friendId, String hisProfileImage, String chatReference) {
        this.friendId = friendId;
        this.hisProfileImage = hisProfileImage;
        this.chatReference = chatReference;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getHisProfileImage() {
        return hisProfileImage;
    }

    public void setHisProfileImage(String hisProfileImage) {
        this.hisProfileImage = hisProfileImage;
    }

    public String getChatReference() {
        return chatReference;
    }

    public void setChatReference(String chatReference) {
        this.chatReference = chatReference;
    }
}
