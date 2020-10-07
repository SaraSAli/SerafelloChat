package com.example.serafellochat.model;

public class Messages {
    private String message;
    private long time;
    private boolean seen;
    private String receiver;
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private int type;

    public Messages(){

    }

    public Messages(String message, String receiver, String sender) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    public Messages(String message, int type, long time, boolean seen, String from) {
        this.message = message;
        this.type = type;
        this.time = time;
        this.seen = seen;
        this.receiver = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
