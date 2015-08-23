package com.angeljedi.popularmovies.loader;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.angeljedi.popularmovies.domain.Review;

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

public class ReviewLoader extends AsyncLoader<List<Review>> {

    private static final String API_KEY = "16bf8a35a93817bb80ca46d39c0ed624";

    private final String LOG_TAG = ReviewLoader.class.getSimpleName();

    private List<Review> reviewList;
    private String movieId;

    public ReviewLoader(Context context, String movieId) {
        super(context);
        reviewList = new ArrayList<>();
        this.movieId = movieId;
    }

    @Override
    public List<Review> loadInBackground() {
        if (movieId == null || movieId.equals("")) {
            return reviewList;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/movie";
            final String API_KEY_PARAM = "api_key";
            final String REVIEW_PATH = "reviews";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(movieId)
                    .appendPath(REVIEW_PATH)
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
                return reviewList;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return reviewList;
            }
            movieJsonStr = buffer.toString();

            JSONObject jsonObject = new JSONObject(movieJsonStr);
            getReviewsFromJson(jsonObject);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving trailer list", e);
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
        return reviewList;
    }

    private void getReviewsFromJson(JSONObject jsonObject) throws JSONException {
        JSONArray array = jsonObject.getJSONArray("results");
        if (array == null) {
            return;
        }

        for (int i = 0, size = array.length(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            Review review = new Review();
            review.setAuthor(object.getString("author"));
            review.setContent(object.getString("content"));
            reviewList.add(review);
        }
    }
}
