package com.gilandeddy.pocketmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        pocketDatabaseHelper.insertMovie(id, 1,title,rating,posterPath);
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
            String sqlInstruction = "CREATE TABLE POCKET(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "INPOCKET INTEGER," +
                    "ID INTEGER," +
                    "TITLE TEXT," +
                    "RATING DOUBLE," +
                    "POSTERPATH TEXT)";
            sqLiteDatabase.execSQL(sqlInstruction);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void insertMovie(int id, int inPocket, String title, double rating, String posterPath){
            ContentValues movieValues = new ContentValues();
            movieValues.put("INPOCKET",1);
            movieValues.put("ID",id);
            movieValues.put("TITLE", title);
            movieValues.put("RATING",rating);
            movieValues.put("POSTERPATH",posterPath);
            getWritableDatabase().insert("POCKET",null,movieValues);
        }
    }
}
