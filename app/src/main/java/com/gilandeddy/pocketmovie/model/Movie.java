package com.gilandeddy.pocketmovie.model;

import java.io.Serializable;

/**
 * Created by gilbert on 5/12/18.
 */

public class Movie implements Serializable{
    private int id;
    private String name;
    private double rating;
    private String posterImageUrl;
    private String releaseDate;
    private String summary;
    private String youtubeID;
    private boolean isInPocket;
    private String detailImageUrl;

    public Movie(int id, String name, double rating, String posterImageUrl, String releaseDate, String summary, String detailImageUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.posterImageUrl = posterImageUrl;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.detailImageUrl = detailImageUrl;
    }
    public Movie(){}

    public Movie(int id, boolean isInPocket, String name, double rating, String posterImageUrl){
        this.id = id;
        this.isInPocket = isInPocket;
        this.name = name;
        this.rating = rating;
        this.posterImageUrl = posterImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getRatingString(){
        return Double.toString(rating);
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getYoutubeID() {
        return youtubeID;
    }

    public boolean isInPocket() {
        return isInPocket;
    }

    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    public void setInPocket(boolean inPocket) {
        isInPocket = inPocket;
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }
}
