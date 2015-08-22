package com.angeljedi.popularmovies.loader;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.util.Utility;

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

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MovieLoader extends AsyncLoader<List<Movie>> {

    private static final String API_KEY = "16bf8a35a93817bb80ca46d39c0ed624";

    private final String LOG_TAG = MovieLoader.class.getSimpleName();

    private List<Movie> mMovieList;
    private String mSortOrder;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MovieLoader(Context context) {
        super(context);
        mMovieList = new ArrayList<>();
        mSortOrder = Utility.getPreferredSort(context);
    }

    @Override
    public List<Movie> loadInBackground() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, mSortOrder)
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
                return mMovieList;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return mMovieList;
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
        return mMovieList;
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
}