package com.example.sampleapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import java.util.List;

public interface apiService {
    @GET("users")
    Call<List<User>> getUsers();
    @POST("users")
    Call<User> createUser(@Body User user);
}
