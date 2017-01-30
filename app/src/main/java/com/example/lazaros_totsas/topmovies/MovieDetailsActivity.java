package com.example.lazaros_totsas.topmovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lazaros_totsas.topmovies.pojo.Movie;
import com.github.edsergeev.TextFloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * Created by ilarrougos-imac on 28/01/17.
 */

public class MovieDetailsActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIE = "movie";

    private Movie mMovie;
    private TextFloatingActionButton fab;
    private ImageView backdrop;
    private ImageView poster;
    private TextView description;
    private TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie serialized");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (TextFloatingActionButton) findViewById(R.id.fab);
        fab.setText(mMovie.getVoteAverage().toString()+"/10");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());


        backdrop = (ImageView) findViewById(R.id.backdrop);
        releaseDate = (TextView) findViewById(R.id.release_date);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);

        description.setText(mMovie.getOverview());
        releaseDate.setText(mMovie.getReleaseDate());
        Picasso.with(this)
                .load(mMovie.getPosterPath())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdropPath())
                .into(backdrop);
    }
}
