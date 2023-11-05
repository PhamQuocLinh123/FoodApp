package com.example.foodapp;

public class CategoryModel {
    public boolean getId;
    private String id;
    private String name;
    private String description;
    private String photo;

    public CategoryModel() {
    }

    public CategoryModel(String name, String description, String photo) {
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public CategoryModel(String id, String name, String description, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
