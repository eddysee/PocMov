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
 * Created by 0504gicarlson on 14/05/2018.
 */

public class PocketedMoviesManager {
    private static final PocketedMoviesManager ourInstance = new PocketedMoviesManager();

    public static PocketedMoviesManager getInstance() {
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

    public void updateMovieInPocket(int id, int inPocket){
        pocketDatabaseHelper.updateMovie(id,inPocket);
    }

    public void deleteMovieInPocket(int id){
        pocketDatabaseHelper.deleteMovie(id);
    }

    /*public void saveRecentMovies(ArrayList<Movie> movies){
        for (Movie movie: movies)
        {
            pocketDatabaseHelper.insertMovie(movie.getId(),0,movie.getName(),movie.getRating(),movie.getPosterImageUrl());
        }
    }*/

    //Should return true if the Movie id exists and inpocket is 1
    //currently not working great
    public boolean isInPocketDatabase(int id){
        String idString = Integer.toString(id);
        Cursor cursor = pocketDatabaseHelper.getReadableDatabase().query("POCKET",new String[]{"_id","INPOCKET"},"ID = " + idString,null,null,null,null);
        if (cursor.moveToNext()){
            if (cursor.getInt(1) == 1){
                Log.d("tag","value of cursor.getint = 1");
                cursor.close();
                return true;
            }
        }
        Log.d("tag ","value of cursor.getint != 1");
        cursor.close();
        return false;
    }

    public ArrayList<Movie> getAllMovies(){
        Cursor cursor = pocketDatabaseHelper.getReadableDatabase().query("POCKET",new String[]{"_id","ID","INPOCKET","TITLE","RATING","POSTERPATH"},
                "INPOCKET = ?", new String[]{"1"},null,null,null,null);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i=0; i <cursor.getCount();i++){
            if (cursor.moveToNext()){
                int id = cursor.getInt(1);
                int inPocket = cursor.getInt(2);
                String title = cursor.getString(3);
                double rating = cursor.getDouble(4);
                String posterPath = cursor.getString(5);
                Movie movie = MovieMaker(id,inPocket,title,rating,posterPath);
                movies.add(movie);
            }
        }
        cursor.close();
        return movies;
    }



    private PocketedMoviesManager() {
    }

    // Constructor for Movie that takes inPocket as an int and turns it into boolean isInPocket
    private Movie MovieMaker(int id, int inPocket, String title, double rating, String posterPath){
        boolean isInPocket = false;
        if (inPocket == 1) {
            isInPocket = true; // converts the SQL 1/0 into a boolean
        }
        Movie movie = new Movie(id,isInPocket,title,rating,posterPath);
        return movie;
    }

    private class PocketDatabaseHelper extends SQLiteOpenHelper{
        private static final String DB_NAME = "PocketedMoviesDataBase";
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
        public void updateMovie(int id, int inPocket){
            ContentValues movieValues = new ContentValues();
            movieValues.put("ID",id);
            movieValues.put("INPOCKET",inPocket);
            String idString = Integer.toString(id);
            try {
                getWritableDatabase().update("POCKET",movieValues,"ID = ?",new String[]{idString});
            }
            catch (SQLException e){
                Log.d("tag", "Error updating db : " + e.getMessage());
            }
        }

        public void deleteMovie(int id){
            String idString = Integer.toString(id);
            getWritableDatabase().delete("POCKET","ID = ?",new String[]{idString});
        }




    }
}
