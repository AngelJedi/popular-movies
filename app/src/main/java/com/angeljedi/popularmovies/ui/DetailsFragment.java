package com.angeljedi.popularmovies.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.adapter.ReviewAdapter;
import com.angeljedi.popularmovies.adapter.TrailerAdapter;
import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.domain.Review;
import com.angeljedi.popularmovies.domain.Trailer;
import com.angeljedi.popularmovies.loader.ReviewLoader;
import com.angeljedi.popularmovies.loader.TrailerLoader;
import com.angeljedi.popularmovies.ui.widgets.TrailerViewHolder;
import com.angeljedi.popularmovies.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment implements TrailerViewHolder.ClickListener {

    public static final String EXTRA_MOVIE = "movie";

    private static final String STATE_MOVIE = "MOVIE";

    private static final int TRAILER_LOADER_ID = 0;
    private static final int REVIEW_LOADER_ID = 1;

    private Movie movie;

    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private int space;

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

        getLoaderManager().initLoader(TRAILER_LOADER_ID, null, new TrailerLoaderCallbacks());
        getLoaderManager().initLoader(REVIEW_LOADER_ID, null, new ReviewLoaderCallbacks());

        trailerRecyclerView = (RecyclerView) view.findViewById(R.id.list_trailers);
        reviewRecyclerView = (RecyclerView) view.findViewById(R.id.list_reviews);

        if (movie != null) {
            TextView titleTextView = (TextView) view.findViewById(R.id.details_title);
            titleTextView.setText(movie.getTitle());

            TextView releaseDateTextView = (TextView) view.findViewById(R.id.details_release_date);
            releaseDateTextView.setText(movie.getReleaseDate());

            TextView ratingTextView = (TextView) view.findViewById(R.id.details_rating);
            ratingTextView.setText(movie.getUserRating() + "/10");

            TextView synopsisTextView = (TextView) view.findViewById(R.id.details_synopsis);
            synopsisTextView.setText(movie.getSynopsis());

            ImageView posterImageView = (ImageView) view.findViewById(R.id.details_poster);
            Picasso.with(getActivity()).load(Movie.THUMBNAIL_SMALL + movie.getThumbnailPath()).into(posterImageView);

            final Button favoriteButton = (Button) view.findViewById(R.id.details_favorite_button);
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavoriteButtonClicked();
                }
            });
        }

        space = getResources().getDimensionPixelSize(R.dimen.poster_spacing);

        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(getActivity());
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
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

        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(getActivity());
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
            }
        });

        reviewAdapter = new ReviewAdapter(getActivity());
        reviewRecyclerView.setAdapter(reviewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIE)) {
            movie = savedInstanceState.getParcelable(STATE_MOVIE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_MOVIE, movie);
        super.onSaveInstanceState(outState);
    }

    private void onFavoriteButtonClicked() {
        Utility.saveNewFavorite(getActivity(), movie.getId());
        String message = movie.getTitle() + " " + getActivity().getResources().getString(R.string.favorite_added);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlayClicked(String trailerKey) {
        Uri uri = Uri.parse("http://www.youtube.com/watch?v=" + trailerKey);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private class TrailerLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Trailer>> {
        @Override
        public Loader<List<Trailer>> onCreateLoader(int i, Bundle bundle) {
            String movieId = "";
            if (movie != null) {
                movieId = movie.getId();
            }
            return new TrailerLoader(getActivity(), movieId);
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
    }

    private class ReviewLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Review>> {
        @Override
        public Loader<List<Review>> onCreateLoader(int i, Bundle bundle) {
            String movieId = "";
            if (movie != null) {
                movieId = movie.getId();
            }
            return new ReviewLoader(getActivity(), movieId);
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
            reviewAdapter.changeReviewList(reviews);
            int reviewCardHeight = getResources().getDimensionPixelSize(R.dimen.review_card_height);
            reviewRecyclerView.getLayoutParams().height = reviewCardHeight * reviews.size();
        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {

        }
    }
}
