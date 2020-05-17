package com.example.client.model;

public class ImageDataTrainingClass {
    private  byte[] data;
    private  String name;
    private  String value;

    public ImageDataTrainingClass() {
    }

    public ImageDataTrainingClass(byte[] data, String name, String value) {
        this.data = data;
        this.name = name;
        this.value = value;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
