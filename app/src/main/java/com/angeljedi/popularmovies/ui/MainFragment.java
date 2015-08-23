package com.angeljedi.popularmovies.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.adapter.MovieAdapter;
import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.loader.MovieLoader;
import com.angeljedi.popularmovies.ui.widgets.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieViewHolder.ClickListener {

    private static final String STATE_MOVIE_LIST = "MOVIE_LIST";

    private static final int LOADER_ID = 0;

    public MainFragment() {
    }

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private int space;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.grid_movies);

        int spanCount = 2;
        space = getResources().getDimensionPixelSize(R.dimen.poster_spacing);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        layoutManager.setSpanSizeLookup(new MovieSpanSizeLookup());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
            }
        });

        movieAdapter = new MovieAdapter(getActivity(), this);
        recyclerView.setAdapter(movieAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIE_LIST)) {
            Parcelable[] movieArray = savedInstanceState.getParcelableArray(STATE_MOVIE_LIST);
            List<Movie> movieList = new ArrayList<>();
            for (Parcelable movie : movieArray) {
                movieList.add((Movie) movie);
            }
            movieAdapter.changeMovieList(movieList);
        }

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        List<Movie> movieList = movieAdapter.getMovieList();
        Parcelable[] movieArray = new Parcelable[movieList.size()];
        for (int i=0; i<movieList.size(); i++) {
            movieArray[i] = movieList.get(i);
        }
        outState.putParcelableArray(STATE_MOVIE_LIST, movieArray);
        super.onSaveInstanceState(outState);
    }

    public void onSortChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        movieAdapter.changeMovieList(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    @Override
    public void onMovieClicked(int position) {
        Movie movie = movieAdapter.getItemAtPosition(position);

        ((Callback) getActivity()).onItemSelected(movie);
    }

    private static class MovieSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        @Override
        public int getSpanSize(int position) {
            return 1;
        }

        @Override
        public int getSpanIndex(int position, int spanCount) {
            return position % spanCount;
        }
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Movie movie);
    }
}
