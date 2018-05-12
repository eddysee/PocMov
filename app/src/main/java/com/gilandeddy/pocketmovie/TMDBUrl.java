package com.gilandeddy.pocketmovie;

/**
 * Created by gilbert on 5/12/18.
 */

public class TMDBUrl {
    static String apiKey = "ea2dcee690e0af8bb04f37aa35b75075";
    static String popularUrl = "https://api.themoviedb.org/3/movie/popular?";
    static String imageUrlHead = "https://image.tmdb.org/t/p/w500/";

    public static String getImageUrlHead() {
        return imageUrlHead;
    }

    static String getPopularUrl (int pageNumber){
        String createdURL = popularUrl + "api_key=" + apiKey + "&page=" + pageNumber;
        return createdURL;
    }

}

