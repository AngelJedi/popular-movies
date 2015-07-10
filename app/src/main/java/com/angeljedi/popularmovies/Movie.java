package com.angeljedi.popularmovies;

import java.io.Serializable;

public class Movie implements Serializable {
    public static final String THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    public static final String THUMBNAIL_SMALL = THUMBNAIL_URL + "w185/";
    public static final String THUMBNAIL_LARGE = THUMBNAIL_URL + "w500/";

    private String title;
    private String thumbnailPath;
    private String synopsis;
    private String userRating;
    private String releaseDate;

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
}
