package com.angeljedi.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final String THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    public static final String THUMBNAIL_SMALL = THUMBNAIL_URL + "w185/";
    public static final String THUMBNAIL_LARGE = THUMBNAIL_URL + "w500/";

    private String title;
    private String thumbnailPath;
    private String synopsis;
    private String userRating;
    private String releaseDate;

    public Movie() {
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        thumbnailPath = in.readString();
        synopsis = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(thumbnailPath);
        dest.writeString(synopsis);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie> () {
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
