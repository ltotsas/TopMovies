package com.example.lazaros_totsas.topmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lazaros_totsas.topmovies.pojo.Movie;
import com.example.lazaros_totsas.topmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilarrougos-imac on 26/01/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context){
        this.mClickHandler = clickHandler;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(mContext)
                .load(movie.getPosterPath())
                .placeholder(R.color.colorAccent)
                .into(holder.posterImageView);
    }


    @Override
    public int getItemCount() {
        if (mMovieList == null)
            return 0;
        else
            return mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.moviePoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie clickedMovie = mMovieList.get(getAdapterPosition());
            mClickHandler.onClick(clickedMovie);
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }
}
