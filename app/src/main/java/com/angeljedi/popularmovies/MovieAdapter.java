package com.angeljedi.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String THUMBNAIL_URL = "http://image.tmdb.org/t/p/w500/";

    private Context mContext;

    public MovieAdapter(Context context, int resourceId, List<Movie> movieList) {
        super(context, resourceId, movieList);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_thumbnail);
        Picasso.with(getContext()).load(THUMBNAIL_URL + movie.getThumbnailPath()).into(imageView);
        return convertView;
    }


}
