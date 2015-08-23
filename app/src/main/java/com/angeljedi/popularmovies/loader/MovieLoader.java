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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MovieLoader extends AsyncLoader<List<Movie>> {

    private static final String API_KEY = "16bf8a35a93817bb80ca46d39c0ed624";

    private final String LOG_TAG = MovieLoader.class.getSimpleName();

    private List<Movie> movieList;
    private String sortOrder;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MovieLoader(Context context) {
        super(context);
        movieList = new ArrayList<>();
        sortOrder = Utility.getPreferredSort(context);
    }

    @Override
    public List<Movie> loadInBackground() {
        URL url = buildUrl();
        if (url == null) {
            return movieList;
        }
        String movieJsonStr = makeApiCall(url);
        if (movieJsonStr.equals("")) {
            return movieList;
        }

        getMovieListFromJsonArray(movieJsonStr);
        return movieList;
    }

    private String makeApiCall(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuilder builder;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            builder = new StringBuilder();
            if (inputStream == null) {
                return "";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return "";
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving movie list", e);
            return "";
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

        return builder.toString();
    }

    private URL buildUrl() {
        final String MOVIE_BASE_URL =
                "http://api.themoviedb.org/3/discover/movie";
        final String SORT_PARAM = "sort_by";
        final String API_KEY_PARAM = "api_key";

        try {
            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sortOrder)
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void getMovieListFromJsonArray(String movieJsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(movieJsonStr);
            JSONArray array = jsonObject.getJSONArray("results");
            if (array == null) {
                return;
            }

            for (int i = 0, size = array.length(); i < size; i++) {
                JSONObject object = array.getJSONObject(i);
                getMovieFromJsonObject(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMovieFromJsonObject(JSONObject object) throws JSONException {
        Movie movie = new Movie();
        movie.setId(object.getString("id"));
        movie.setTitle(object.getString("original_title"));
        movie.setThumbnailPath(object.getString("poster_path"));
        movie.setSynopsis(object.getString("overview"));
        movie.setUserRating(object.getString("vote_average"));
        movie.setReleaseDate(object.getString("release_date"));
        movieList.add(movie);
    }
}
