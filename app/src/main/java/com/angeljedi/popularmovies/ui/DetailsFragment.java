package com.angeljedi.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.adapter.TrailerAdapter;
import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.domain.Trailer;
import com.angeljedi.popularmovies.loader.TrailerLoader;
import com.angeljedi.popularmovies.ui.widgets.TrailerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Trailer>>, TrailerViewHolder.ClickListener {

    public static final String EXTRA_MOVIE = "movie";

    private static final int TRAILER_LOADER_ID = 0;

    private Movie movie;

    private RecyclerView trailerRecyclerView;

    private TrailerAdapter trailerAdapter;

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

        getLoaderManager().initLoader(TRAILER_LOADER_ID, null, this);

        trailerRecyclerView = (RecyclerView) view.findViewById(R.id.list_trailers);

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

//        space = getResources().getDimensionPixelSize(R.dimen.poster_spacing);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trailerRecyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.left = space;
//                outRect.right = space;
//                outRect.bottom = space;
//                outRect.top = space;
//            }
//        });

        trailerAdapter = new TrailerAdapter(getActivity(), this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        return view;
    }

    @Override
    public Loader<List<Trailer>> onCreateLoader(int i, Bundle bundle) {
        return new TrailerLoader(getActivity(), movie.getId());
    }

    @Override
    public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailers) {
        trailerAdapter.changeTrailerList(trailers);
        int trailerCardHeight = getResources().getDimensionPixelSize(R.dimen.trailer_card_height);
        trailerRecyclerView.getLayoutParams().height = trailerCardHeight * trailers.size();
    }

    @Override
    public void onLoaderReset(Loader<List<Trailer>> loader) {

    }

    @Override
    public void onTrailerClicked(int position) {

    }
}
