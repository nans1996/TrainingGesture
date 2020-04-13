package com.example.client;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ImageInterface {
    @GET("api/translate")
    Call<String> translateImage();
}
