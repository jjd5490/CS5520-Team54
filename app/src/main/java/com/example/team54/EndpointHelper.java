package com.example.team54;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface EndpointHelper {

    @GET("games?per_page=100")
    Call<GameResponseModel> getGameModels();
}
