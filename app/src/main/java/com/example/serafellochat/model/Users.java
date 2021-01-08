package com.example.serafellochat.model;

public class Users {
    private String status; //online or offline
    private String username;
    private String image;
    private String id;
    private String search;

    public Users(String id, String username, String image, String status, String search) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.status = status;
        this.search = search;
    }

    public Users() {

    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}