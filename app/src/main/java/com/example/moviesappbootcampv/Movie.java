package com.example.moviesappbootcampv;

public class Movie {
    private String title;
    private String releaseDate;
    private String overview;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Movie(String title, String releaseDate, String overview, String image) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.image = image;
    }
}
