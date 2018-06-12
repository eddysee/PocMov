package com.gilandeddy.pocketmovie.model;

/**
 * This class TMDBUrl hosts and gets the related URLs from 'The Movie Date Base'
 * TMDB - The Movie Data Base
 * It includes methods that
 */

/** Initialising the String URLs
 *
 */

public class TMDBUrl {
    private static String apiKey = "ea2dcee690e0af8bb04f37aa35b75075";
    private static String baseURL = "https://api.themoviedb.org/3/movie/";
    private static String imageUrlHead = "https://image.tmdb.org/t/p/w500";
    private static String searchURL = "https://api.themoviedb.org/3/search/movie?query=";

    /** This method gets the URL header for the movie image
     *
     * @return url head
     */
    public static String getImageUrlHead() {
        return imageUrlHead; }

    /** This method returns the URL for the popular movies on TMDB
     *
     * @param pageNumber
     * @return popular movie URl with page number
     */
    public static String getPopularUrl(int pageNumber) {
        String createdURL = baseURL + "popular?api_key=" + apiKey + "&page=" + pageNumber;
        return createdURL;
    }

    /** This method returns the complete URL for the movie trailer
     * It requires the movie ID to construct the correct URL
     * @param id
     * @return movie URL
     */
    public static String getVideoUrl(int id) {
        String createdURL = baseURL + id + "/videos?api_key=" + apiKey;
        return createdURL;
    }

    /** This method returns the detail image URL
     * It requires the movie ID to construct the correct URL
     *
     * @param id
     * @return
     */
    public static String getDetailsURL(int id) {
        String createdURL = baseURL + id + "?api_key=" + apiKey;
        return createdURL;
    }

    /** This method returns the search URl for query for a movie object arraylist
     * It requires String query and page number to construct the correct URL
     *
     *
     * @param pageNumber
     * @param query
     * @return search URL with query
     */
    public static String getSearchURL(int pageNumber, String query) {
        String createdURL = searchURL + query + "&page=" + pageNumber + "&api_key=" + apiKey;
        return createdURL;
    }


}

