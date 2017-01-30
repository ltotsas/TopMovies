package com.example.lazaros_totsas.topmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lazaros_totsas.topmovies.adapter.MovieAdapter;
import com.example.lazaros_totsas.topmovies.api.ApiClient;
import com.example.lazaros_totsas.topmovies.pojo.Movie;
import com.example.lazaros_totsas.topmovies.api.ApiService;
import com.example.lazaros_totsas.topmovies.pojo.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ImageView mImageView;
    private int sortSelection = 1;
    private static final String TAG = MoviesActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mImageView = (ImageView) findViewById(R.id.no_internet);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MovieAdapter(this,this.getBaseContext());
        mRecyclerView.setAdapter(mAdapter);
        List<Movie> movies = new ArrayList<>();
        mAdapter.setMovieList(movies);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.sort);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MoviesActivity.this)
                        .title(R.string.sort_select)
                        .items(R.array.preference_values)
                        .itemsColor(ContextCompat.getColor(MoviesActivity.this, R.color.colorPrimary))
                        .positiveColor(ContextCompat.getColor(MoviesActivity.this, R.color.colorPrimaryDark))
                        .itemsCallbackSingleChoice(sortSelection, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        getTopRatedMovies();
                                        sortSelection = 0;
                                        break;
                                    case 1:
                                        getMostPopularMovies();
                                        sortSelection = 1;
                                        break;
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });
        /**
         * Don't understand why even though i disable
         * visibility still the image can be seen in
         * the background when changing filter
        **/
        if (isNetworkAvailable(MoviesActivity.this) == true) {
            mImageView.setVisibility(View.GONE);
            getMostPopularMovies();
        }
        else {
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    private void getTopRatedMovies(){
        try {
            ApiService apiService =
                    ApiClient.getClient().create(ApiService.class);

            Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.IMDB_API_KEY);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    mAdapter.setMovieList(movies);
                    mRecyclerView.smoothScrollToPosition(0);
                }
                @Override
                public void onFailure(Call<MoviesResponse>call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error",e.getMessage());
        }
    }
    private void getMostPopularMovies() {
        try {
            ApiService apiService =
                    ApiClient.getClient().create(ApiService.class);

            Call<MoviesResponse> call = apiService.getMostPopularMovies(BuildConfig.IMDB_API_KEY);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    mAdapter.setMovieList(movies);
                    mRecyclerView.smoothScrollToPosition(0);
                }
                @Override
                public void onFailure(Call<MoviesResponse>call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error",e.getMessage());
        }
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);
        context.startActivity(intent);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}