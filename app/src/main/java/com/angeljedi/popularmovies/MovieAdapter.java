package com.angeljedi.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
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

        TextView tvTitle = (TextView) convertView.findViewById(R.id.movie_title);
        tvTitle.setText(movie.getTitle());
        return convertView;
    }
}
