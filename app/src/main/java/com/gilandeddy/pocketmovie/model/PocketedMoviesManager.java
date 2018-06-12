package com.gilandeddy.pocketmovie.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * @ Gilbert & Eddy
 * This class PocketMovieManager handles the SQL for the Pocket
 * It includes and SQLite Database constructor and handler
 * Provides service for adding and deleting movies from Pocket
 */



public class PocketedMoviesManager {
    /**
     *
     */
    private static final PocketedMoviesManager ourInstance = new PocketedMoviesManager();

    /** This method gets the instance of the PocketMovieManager
     *
     * @return instance
     */
    public static PocketedMoviesManager getInstance() {
        return ourInstance;
    }

    /** Initialising the PocketDatabaseHelper
     *
     */
    private PocketDatabaseHelper pocketDatabaseHelper;

    /** An initialized instance of the appropriate subclass
     *
     * @param context
     */
    public void initWithContext(Context context) {
        if (pocketDatabaseHelper == null) {
            pocketDatabaseHelper = new PocketDatabaseHelper(context);
        }
    }

    /** This method adds movie to the Pocket database with the following parameters
     *
     * @param id
     * @param inPocket
     * @param title
     * @param rating
     * @param posterPath
     */
    public void addMovieToPocket(int id, int inPocket, String title, double rating, String posterPath) {
        pocketDatabaseHelper.insertMovie(id, inPocket, title, rating, posterPath);
    }

    /* This method updates the movies in the Pocket
     *
     * @param id
     * @param inPocket

    public void updateMovieInPocket(int id, int inPocket) {
        pocketDatabaseHelper.updateMovie(id, inPocket);
    }
     */

    /** This method deletes the movies from the Pocket database with movie id
     *
     * @param id
     */
    public void deleteMovieInPocket(int id) {
        pocketDatabaseHelper.deleteMovie(id);
    }

    /** This method returns true if the movie is in the SQL Database and the value of inPocket is 1
     *
     * @param id
     * @return boolean
     */
    public boolean isInPocketDatabase(int id) {
        String idString = Integer.toString(id);
        Cursor cursor = pocketDatabaseHelper.getReadableDatabase().query("POCKET", new String[]{"_id", "INPOCKET"}, "ID = " + idString, null, null, null, null);
        if (cursor.moveToNext()) {
            if (cursor.getInt(1) == 1) {
                //Log.d("tag", "value of cursor.getint = 1");
                cursor.close();
                return true;
            }
        }
       // Log.d("tag ", "value of cursor.getint != 1");
        cursor.close();
        return false;
    }

    /** This method gets all the movies where inPocket is 1 in the SQLite DB in form of movie object array list with
     * the selected elements:
     * Movie ID
     * Movie Boolean Value for INPOCKET
     * Movie TITLE
     * Movie POSTERPATH
     *
     *
     *
     *
     * @return movie object array list
     */
    public ArrayList<Movie> getAllMovies() {
        Cursor cursor = pocketDatabaseHelper.getReadableDatabase().query("POCKET", new String[]{"_id", "ID", "INPOCKET", "TITLE", "RATING", "POSTERPATH"},
                "INPOCKET = ?", new String[]{"1"}, null, null, null, null);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            if (cursor.moveToNext()) {
                int id = cursor.getInt(1);
                int inPocket = cursor.getInt(2);
                String title = cursor.getString(3);
                double rating = cursor.getDouble(4);
                String posterPath = cursor.getString(5);
                Movie movie = MovieMaker(id, inPocket, title, rating, posterPath);
                movies.add(movie);
            }
        }
        cursor.close();
        return movies;
    }

    /**
     *
     */

    private PocketedMoviesManager() {
    }

    /** Pseudo-Constructor for Movie that takes inPocket as an int and turns it into boolean isInPocket
     *
     * @param id
     * @param inPocket
     * @param title
     * @param rating
     * @param posterPath
     * @return movie object
     */

    private Movie MovieMaker(int id, int inPocket, String title, double rating, String posterPath) {
        boolean isInPocket = false;
        if (inPocket == 1) {
            isInPocket = true; // converts the SQL 1/0 into a boolean
        }
        Movie movie = new Movie(id, isInPocket, title, rating, posterPath);
        return movie;
    }

    /** This class PocketDatabaseHelper creates and updates the Pocket Database
     *
     */
    private class PocketDatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "PocketedMoviesDataBase";
        private static final int DB_VERSION = 1;

        PocketDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        /** This methods createss the SQLiteDatabase with certain elements of the movie object
         *
         * @param sqLiteDatabase
         */

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                String sqlInstruction = "CREATE TABLE POCKET (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID INTEGER," +
                        "INPOCKET INTEGER," +
                        "TITLE TEXT," +
                        "RATING DOUBLE," +
                        "POSTERPATH TEXT)";
                sqLiteDatabase.execSQL(sqlInstruction);
            } catch (SQLException e) {
                Log.d("tag", "Error creating db : " + e.getMessage());
            }
        }

        /** This method is called upgrade the database.
         * The implementation can be use the method to drop tables, add tables,
         * or do anything else it needs to upgrade to the new table.
         *
         * @param sqLiteDatabase
         * @param i
         * @param i1
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        /** This method inserts Movie to the database
         * The SQLite database includes the following
         * parameters
         *
         * @param id
         * @param inPocket
         * @param title
         * @param rating
         * @param posterPath
         */
        public void insertMovie(int id, int inPocket, String title, double rating, String posterPath) {
            ContentValues movieValues = new ContentValues();
            movieValues.put("ID", id);
            movieValues.put("INPOCKET", inPocket);
            movieValues.put("TITLE", title);
            movieValues.put("RATING", rating);
            movieValues.put("POSTERPATH", posterPath);
            try {
                getWritableDatabase().insertOrThrow("POCKET", null, movieValues);
            } catch (SQLException e) {
                Log.d("tag", "Error inserting db : " + e.getMessage());
            }
        }

        /** This method deletes the movie from the Pocket database
         *
         * @param id
         */
        public void deleteMovie(int id) {
            String idString = Integer.toString(id);
            getWritableDatabase().delete("POCKET", "ID = ?", new String[]{idString});
        }
        /* This method updates the movie as status of being in the Pocket database or not
         *
         * @param id
         * @param inPocket

        public void updateMovie(int id, int inPocket) {
            ContentValues movieValues = new ContentValues();
            movieValues.put("ID", id);
            movieValues.put("INPOCKET", inPocket);
            String idString = Integer.toString(id);
            try {
                getWritableDatabase().update("POCKET", movieValues, "ID = ?", new String[]{idString});
            } catch (SQLException e) {
                Log.d("tag", "Error updating db : " + e.getMessage());
            }
        }*/
    }
}
