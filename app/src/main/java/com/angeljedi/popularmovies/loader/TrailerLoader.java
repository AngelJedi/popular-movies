package com.angeljedi.popularmovies.loader;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.angeljedi.popularmovies.domain.Trailer;

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
public class TrailerLoader extends AsyncLoader<List<Trailer>> {

    private static final String API_KEY = "16bf8a35a93817bb80ca46d39c0ed624";

    private final String LOG_TAG = TrailerLoader.class.getSimpleName();

    private List<Trailer> trailerList;
    private String movieId;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TrailerLoader(Context context, String movieId) {
        super(context);
        trailerList = new ArrayList<>();
        this.movieId = movieId;
    }

    @Override
    public List<Trailer> loadInBackground() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/movie";
            final String API_KEY_PARAM = "api_key";
            final String TRAILER_PATH = "videos";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(movieId)
                    .appendPath(TRAILER_PATH)
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
                return trailerList;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return trailerList;
            }
            movieJsonStr = buffer.toString();

            JSONObject jsonObject = new JSONObject(movieJsonStr);
            getTrailersFromJson(jsonObject);

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
        return trailerList;
    }

    private void getTrailersFromJson(JSONObject jsonObject) throws JSONException {
        JSONArray array = jsonObject.getJSONArray("results");
        if (array == null) {
            return;
        }

        for (int i = 0, size = array.length(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            Trailer trailer = new Trailer();
            trailer.setName(object.getString("name"));
            trailer.setKey(object.getString("key"));
            trailer.setType(object.getString("type"));
            trailerList.add(trailer);
        }
    }
}
