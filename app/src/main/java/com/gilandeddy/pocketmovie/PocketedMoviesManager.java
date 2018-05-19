package com.gilandeddy.pocketmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 0504gicarlson on 14/05/2018.
 */

class PocketedMoviesManager {
    private static final PocketedMoviesManager ourInstance = new PocketedMoviesManager();

    static PocketedMoviesManager getInstance() {
        return ourInstance;
    }
    private PocketDatabaseHelper pocketDatabaseHelper;

    public void initWithContext(Context context){
        if (pocketDatabaseHelper == null ){
            pocketDatabaseHelper = new PocketDatabaseHelper(context);
        }
    }

    public void addMovieToPocket(int id, int inPocket, String title, double rating, String posterPath){
        pocketDatabaseHelper.insertMovie(id, inPocket,title,rating,posterPath);
    }

    public void saveRecentMovies(ArrayList<Movie> movies){
        for (Movie movie: movies)
        {
            pocketDatabaseHelper.insertMovie(movie.getId(),0,movie.getName(),movie.getRating(),movie.getPosterImageUrl());
        }
    }

    private PocketedMoviesManager() {
    }

    private class PocketDatabaseHelper extends SQLiteOpenHelper{
        private static final String DB_NAME = "PocketedMoviesDB";
        private static final int DB_VERSION = 1;

        PocketDatabaseHelper(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }


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
            }
            catch (SQLException e){
                Log.d("tag", "Error creating db : "+e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void insertMovie(int id, int inPocket, String title, double rating, String posterPath){
            ContentValues movieValues = new ContentValues();
            movieValues.put("ID",id);
            movieValues.put("INPOCKET",inPocket);
            movieValues.put("TITLE", title);
            movieValues.put("RATING",rating);
            movieValues.put("POSTERPATH",posterPath);
            try {
                getWritableDatabase().insertOrThrow("POCKET", null, movieValues);
            }
            catch (SQLException e){
                Log.d("tag", "Error inserting db : "+e.getMessage());
            }
        }
    }
}
