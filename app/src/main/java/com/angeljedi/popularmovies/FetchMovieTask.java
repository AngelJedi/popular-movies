package com.angeljedi.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {

    private static final String API_KEY = "16bf8a35a93817bb80ca46d39c0ed624";

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private Activity mActivity;
    private List<Movie> mMovieList;

    public FetchMovieTask(Activity activity) {
        mActivity = activity;
        mMovieList = new ArrayList<>();
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        String sortBy = "popularity.desc";

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sortBy)
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            movieJsonStr = buffer.toString();

            JSONObject jsonObject = new JSONObject(movieJsonStr);
            getMoviesFromJson(jsonObject);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving movie list", e);
        } catch (JSONException e ) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    private void getMoviesFromJson(JSONObject jsonObject) throws JSONException {
        JSONArray array = jsonObject.getJSONArray("results");
        if (array == null) {
            return;
        }

        for (int i = 0, size = array.length(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            Movie movie = new Movie();
            movie.setTitle(object.getString("original_title"));
            movie.setThumbnailPath(object.getString("poster_path"));
            movie.setSynopsis(object.getString("overview"));
            movie.setUserRating(object.getString("vote_average"));
            movie.setReleaseDate(object.getString("release_date"));
            mMovieList.add(movie);
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        MovieAdapter adapter = new MovieAdapter(mActivity, android.R.layout.simple_list_item_1, mMovieList);
        adapter.notifyDataSetChanged();
        GridView grid = (GridView)mActivity.findViewById(R.id.grid_movies);
        grid.setAdapter(adapter);
    }
}
