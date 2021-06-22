package com.aoslec.androidproject.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ClothesSQLite  extends SQLiteOpenHelper {


    public ClothesSQLite(Context context) {
        super(context, "Clothes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE clothes(Temperature TEXT PRIMARY KEY, item1 TEXT, item2 TEXT, item3 TEXT, item4 TEXT, item5 TEXT);";
        Log.v("ggg","onCreate");
        db.execSQL(query);

        try {
            String query1 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('-10이하', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query1);
            Log.v("ggg","insert1");
            String query2 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('-5~0도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query2);
            Log.v("ggg","insert2");
            String query3 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('0도~5도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query3);
            Log.v("ggg","insert3");
            String query4 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('5도~10도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query4);
            String query5 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('10도~15도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query5);
            String query6 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('15도~20도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query6);
            String query7 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('20도~25도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query7);
            String query8 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('25도~30도', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query8);
            String query9 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('30도이상', 'item1.png','item2.png','item3.png','item4.png','item5.png');";
            db.execSQL(query9);
            Log.v("ggg","insert9");
            close();
            Log.v("ggg","insert");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS clothes";
        db.execSQL(query);
        onCreate(db);

    }
}
