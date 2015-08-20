package com.angeljedi.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.ui.widgets.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;

    private List<Movie> movieList = new ArrayList<>();
    private MovieViewHolder.ClickListener clickListener;

    public MovieAdapter(Context context, MovieViewHolder.ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(context).load(Movie.THUMBNAIL_LARGE + movie.getThumbnailPath()).into(holder.getPosterImageView());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void changeMovieList(List<Movie> movieList) {
        if (movieList != null) {
            this.movieList = movieList;
            notifyDataSetChanged();
        }
    }

    public Movie getItemAtPosition(int position) {
        return movieList.get(position);
    }
}
