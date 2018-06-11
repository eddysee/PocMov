package com.gilandeddy.pocketmovie.model;

import java.util.ArrayList;

/**
 * Created by 0504gicarlson on 11/06/2018.
 */

public class SearchedMovies {
    private static final SearchedMovies ourInstance = new SearchedMovies();
    private ArrayList<Movie> movies = new ArrayList<>();

    public static SearchedMovies getInstance() {
        return ourInstance;
    }

    private SearchedMovies() {
    }

    public void addToSearchedMovies(ArrayList<Movie> newMovies) {
        movies.addAll(newMovies);

    }

    public ArrayList<Movie> getSearchedMovies() {
        return movies;
    }
}
