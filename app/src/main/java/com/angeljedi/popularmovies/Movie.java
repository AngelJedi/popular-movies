package com.angeljedi.popularmovies;

public class Movie {
    private String title;
    private String thumbnailPath;
    private String synopsis;
    private String userRating;
    private String releastDate;

    public String getReleastDate() {
        return releastDate;
    }

    public void setReleastDate(String releastDate) {
        this.releastDate = releastDate;
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
