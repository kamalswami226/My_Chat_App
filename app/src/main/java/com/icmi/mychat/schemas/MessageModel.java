package com.icmi.mychat.schemas;

public class MessageModel {

    private String message;
    private long time;
    private int type;
    private boolean seen;
    private String senderId;


    public MessageModel() {
    }

    public MessageModel(String message, long time, int type, boolean seen, String senderId) {
        this.message = message;
        this.time = time;
        this.type = type;
        this.seen = seen;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
