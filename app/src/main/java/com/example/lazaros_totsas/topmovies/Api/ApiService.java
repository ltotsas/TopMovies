package com.example.lazaros_totsas.topmovies.Api;

import com.example.lazaros_totsas.topmovies.POJO.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ilarrougos-imac on 27/01/17.
 */

public interface ApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getMostPopularMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

}
