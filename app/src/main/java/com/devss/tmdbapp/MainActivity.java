package com.devss.tmdbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    // https://image.tmdb.org/t/p/w185

    public static String BASE_URL = "https://api.themoviedb.org";
    public static String API_KEY = "80042d5849e24d561f9ccd7beed28805";
    public static String LANGUAGE = "en-us";
    public static String CATEGORY = "popular";
    public static int PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ListView listView = findViewById(R.id.list_view);

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
//                List<String> list = new ArrayList<>();
                for (int i=0; i<listOfMovies.size(); i++) {
                    mNames.add(listOfMovies.get(i).getTitle());
                    mImageUrls.add("https://image.tmdb.org/t/p/w185"+listOfMovies.get(i).getPosterPath());
                }

//                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
//                listView.setAdapter(adapter);

                initRecyclerView();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.list_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}