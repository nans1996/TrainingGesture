package com.example.client.model;

public class ImageDataClass {

    private String name;
    private byte[] data;
    private boolean auth;

    public ImageDataClass() {
    }

    public ImageDataClass(String name, byte[] data, boolean auth) {
        this.name = name;
        this.data = data;
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean getValue() {
        return auth;
    }

    public void setValue(boolean auth) {
        this.auth = auth;
    }
}