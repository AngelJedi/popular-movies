package com.angeljedi.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.angeljedi.popularmovies.R;

import java.util.HashSet;
import java.util.Set;

public class Utility {

    public static String getPreferredSort(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.pref_sort_popular_value));
    }

    public static Set<String> getFavoriteSet(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getStringSet(context.getString(R.string.pref_favorites_key), new HashSet<String>());
    }

    public static void saveNewFavorite(Context context, String movieId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favoriteSet = prefs.getStringSet(context.getString(R.string.pref_favorites_key), new HashSet<String>());
        favoriteSet.add(movieId);
        prefs.edit().putStringSet(context.getString(R.string.pref_favorites_key), favoriteSet).apply();
    }
}
