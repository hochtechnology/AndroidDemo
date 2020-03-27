package com.hochtechnology.skeleton.reteofit;

import com.hochtechnology.skeleton.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://picsum.photos/v2/";

    @GET("list")
    Call<List<User>> getHeroes();
}