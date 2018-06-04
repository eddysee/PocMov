package com.gilandeddy.pocketmovie.model;

import java.util.ArrayList;

/**
 * Created by 0504gicarlson on 4/06/2018.
 */

public class RecentMovies {
    private static final RecentMovies ourInstance = new RecentMovies();
    private ArrayList<Movie> movies = new ArrayList<>();

    public static RecentMovies getInstance() {
        return ourInstance;
    }

    private RecentMovies() {
    }

    public void addToRecentMovies (ArrayList<Movie> newMovies){
        movies.addAll(newMovies);

    }
    public ArrayList<Movie> getRecentMovies(){
        return movies;
    }

}
