package com.example.client.repos;


import com.example.client.model.ImageDataClass;
import com.example.client.model.ImageDataTrainingClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageInterface {
    @POST("api/translate")
    Call<String> translateImage(@Body ImageDataClass img);

    @POST("api/training")
    Call<String> trainingImage(@Body ImageDataTrainingClass img);
}
