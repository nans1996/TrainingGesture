package com.example.client;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageInterface {
    @POST("api/translate")
    Call translateImage(@Body ImageDataClass img);
}
