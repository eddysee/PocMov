package com.gilandeddy.pocketmovie.model;

import java.util.ArrayList;

/**
 * @author Gilbert & Eddy
 * This class RecentMovies handles the recent movies which are then places in the Recent Fragment
 */

public class RecentMovies {
    private static final RecentMovies ourInstance = new RecentMovies();
    private ArrayList<Movie> movies = new ArrayList<>();

    /** The method returns the instance of RecentMovies
     *
     * @return recent movies
     */
    public static RecentMovies getInstance() {
        return ourInstance;
    }

    /**
     *
     */
    private RecentMovies() {
    }

    /** This method adds the movie object arraylist to the recent movies
     *
     * @param newMovies
     */
    public void addToRecentMovies(ArrayList<Movie> newMovies) {
        movies.addAll(newMovies);

    }

    /** This methods gets the recent movies
     *
     * @return recent movies
     */
    public ArrayList<Movie> getRecentMovies() {
        return movies;
    }

}
