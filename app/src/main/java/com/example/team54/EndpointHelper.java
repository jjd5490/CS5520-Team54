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

    @GET("games")
    Call<GameResponseModel> getAllSeasonGames(@Query("team_ids[]") int id,
                                              @Query("seasons[]") int year,
                                              @Query("per_page") int count);

    @GET("games")
    Call<GameResponseModel> getGamesPostFilter(@Query("team_ids[]") int id,
                                               @Query("seasons[]") int year,
                                               @Query("postseason") boolean post,
                                               @Query("per_page") int count);
}
