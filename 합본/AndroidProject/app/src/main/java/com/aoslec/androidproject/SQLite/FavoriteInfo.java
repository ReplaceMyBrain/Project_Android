package com.aoslec.androidproject.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteInfo extends SQLiteOpenHelper {

    public FavoriteInfo(Context context){
        super(context,"FavoriteInfo.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE favorite(id INTEGER PRIMARY KEY AUTOINCREMENT,location TEXT, latitude TEXT, longitude TEXT, heart TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS favorite;";
        db.execSQL(query);
        onCreate(db);
    }
}
