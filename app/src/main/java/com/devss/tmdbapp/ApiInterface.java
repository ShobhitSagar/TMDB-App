package com.devss.tmdbapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@FunctionalInterface
public interface ApiInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=80042d5849e24d561f9ccd7beed28805&language=en-US&page=1
    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
