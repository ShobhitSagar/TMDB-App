package com.devss.tmdbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "80042d5849e24d561f9ccd7beed28805";
    public static String LANGUAGE = "en-us";
    public static String CATEGORY = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = apiInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.Result> listOfMovies = results.getResults();

                Log.d(TAG, "onResponse: "+ listOfMovies.get(3).getTitle());

                // In ListView
                List<String> list = new ArrayList<>();
                for (int i=0; i<listOfMovies.size(); i++) {
                    list.add(listOfMovies.get(i).getTitle());
                }

                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });


    }
}