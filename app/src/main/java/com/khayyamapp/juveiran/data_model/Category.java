package com.khayyamapp.juveiran.data_model;

public class Category {
    private int image;
    private String name;
    private String id;

    public Category(int image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }


    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
