package com.angeljedi.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    public static final String EXTRA_MOVIE = "movie";

    private Movie movie;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        Bundle args = getArguments();
        if (args != null) {
            movie = args.getParcelable(EXTRA_MOVIE);
        }

        if (movie != null) {
            TextView tvTitle = (TextView) view.findViewById(R.id.details_title);
            tvTitle.setText(movie.getTitle());

            TextView tvReleaseDate = (TextView) view.findViewById(R.id.details_release_date);
            tvReleaseDate.setText(movie.getReleaseDate());

            TextView tvRating = (TextView) view.findViewById(R.id.details_rating);
            tvRating.setText(movie.getUserRating() + "/10");

            TextView tvSynopsis = (TextView) view.findViewById(R.id.details_synopsis);
            tvSynopsis.setText(movie.getSynopsis());

            ImageView ivPoster = (ImageView) view.findViewById(R.id.details_poster);
            Picasso.with(getActivity()).load(Movie.THUMBNAIL_SMALL + movie.getThumbnailPath()).into(ivPoster);
        }

        return view;
    }

}