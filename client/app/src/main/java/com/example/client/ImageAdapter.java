package com.example.client;

import com.example.client.model.ImageDataClass;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ImageAdapter extends TypeAdapter<ImageDataClass> {
    @Override
    public void write(JsonWriter out, ImageDataClass value) throws IOException {
        out.beginArray();
        out.beginObject();
        out.name("value");
        out.beginObject();
        out.name("data");
        out.value(value.getData().toString());
        out.name("name");
        out.value(value.getName());
        out.endObject();
        out.name("path");
        out.value("TranslateActivity");
        out.name("op");
        out.value("replace");
        out.endObject();
        out.endArray();
    }

    @Override
    public ImageDataClass read(JsonReader in) throws IOException {
        return null;
    }
}
