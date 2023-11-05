package com.example.foodapp;

public class UserModel {
    private String id;
    private String email;
    private String username;
    private String phone;
    private String photo;

    public UserModel() {
    }

    public UserModel(String id, String email, String username, String phone, String photo) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.photo = photo;
    }

    public UserModel(String email, String username, String phone, String photo) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
