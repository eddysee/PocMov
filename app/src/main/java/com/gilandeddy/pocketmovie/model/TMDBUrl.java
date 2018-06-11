package com.gilandeddy.pocketmovie.model;

/**
 * Created by gilbert on 5/12/18.
 */

public class TMDBUrl {
    private static String apiKey = "ea2dcee690e0af8bb04f37aa35b75075";
    private static String baseURL = "https://api.themoviedb.org/3/movie/";
    private static String imageUrlHead = "https://image.tmdb.org/t/p/w500";
    private static String searchURL = "https://api.themoviedb.org/3/search/movie?query=";

    public static String getImageUrlHead() {
        return imageUrlHead; // necessary ?
    }

    public static String getPopularUrl (int pageNumber){
        String createdURL = baseURL + "popular?api_key=" + apiKey + "&page=" + pageNumber;
        return createdURL;
    }
    public static String getVideoUrl (int id){
        String createdURL = baseURL + id + "/videos?api_key=" + apiKey;
        return createdURL;
    }

    public static String getDetailsURL (int id) {
        String createdURL = baseURL + id + "?api_key=" + apiKey;
        return createdURL;
    }

    public static String getSearchURL (int pageNumber, String query){
        String createdURL = searchURL + query + "&page=" + pageNumber + "&api_key=" + apiKey;
        return createdURL;
    }


}

