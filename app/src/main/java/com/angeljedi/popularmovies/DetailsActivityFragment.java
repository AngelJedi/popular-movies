package com.angeljedi.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public static final String EXTRA_MOVIE = "movie";

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        Movie movie = (Movie) bundle.getSerializable(EXTRA_MOVIE);

        if (movie != null) {
            TextView tvTitle = (TextView) view.findViewById(R.id.detail_title);
            tvTitle.setText(movie.getTitle());
        }

        return view;
    }

}
