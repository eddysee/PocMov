package com.gilandeddy.pocketmovie.model;

import java.io.Serializable;

/**
 * @author Gilbert and Eddy
 * This class manages the object [Movie]
 * The response recieved from THBD for popular movies is in the form of an
 * JSON Array of object. This class also includes the getters and setters of certain
 * elements of movie object.
 */

/**
 * Encapsulation of the movie object elements
 */
public class Movie implements Serializable {
    private int id;
    private String name;
    private double rating;
    private String posterImageUrl;
    private String releaseDate;
    private String summary;
    private String youtubeID;
    private boolean isInPocket;
    private String detailImageUrl;
    private String eddy;




    /**
     * Constructors for the movie elements
     * @param id
     * @param name
     * @param rating
     * @param posterImageUrl
     * @param releaseDate
     * @param summary
     * @param detailImageUrl
     */
    public Movie(int id, String name, double rating, String posterImageUrl, String releaseDate, String summary, String detailImageUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.posterImageUrl = posterImageUrl;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.detailImageUrl = detailImageUrl;
    }

    /**
     *
     */
    public Movie() {
    }

    /**
     * Constructors for movie elements in Pocket
     * @param id
     * @param isInPocket
     * @param name
     * @param rating
     * @param posterImageUrl
     */
    public Movie(int id, boolean isInPocket, String name, double rating, String posterImageUrl) {
        this.id = id;
        this.isInPocket = isInPocket;
        this.name = name;
        this.rating = rating;
        this.posterImageUrl = posterImageUrl;
    }

    /**
     * Getter for the movie ID Integer
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the movie Name String
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *  Getter for the movie Rating Double
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /** Getter for the movie Rating in String
     *
     * @return rating
     */
    public String getRatingString() {
        return Double.toString(rating);
    }

    /** Getter for the movie Front Poster Image Url in String
     *
     * @return poster image url
     */
    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    /** Getter for the movie Release Date in String
     *
     * @return release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /** Getter for the movie Summary in String
     *
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /** Getter for the movie Trailer Youtube ID in String
     *
     * @return movie trailer Youtube id
     */
    public String getYoutubeID() {
        return youtubeID;
    }

    /** Getter for boolean value for is in Pocket
     *
     * @return boolean value for is is Pocker
     */
    public boolean isInPocket() {
        return isInPocket;
    }

    /** Getter for the movie Details Poster Image Url in String
     *
     * @return detail image url
     */
    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    /** Setter for in Pocker - Boolean
     *
     * @param inPocket
     */
    public void setInPocket(boolean inPocket) {
        isInPocket = inPocket;
    }

    /** Setter for movie Youtube trailer id
     *
     * @param youtubeID
     */
    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }

    /** Setter for movie Release date
     *
     * @param releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /** Setter for movie Summary
     *
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /** Setter for movie Detail image
     *
     * @param detailImageUrl
     */
    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }
}
