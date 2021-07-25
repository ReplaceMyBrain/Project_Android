package com.aoslec.androidproject.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClothesSQLite extends SQLiteOpenHelper {


    public ClothesSQLite(Context context) {
        super(context, "Clothes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE clothes(Temperature TEXT PRIMARY KEY, item1 TEXT, item2 TEXT, item3 TEXT, item4 TEXT, item5 TEXT);";
        db.execSQL(query);

        try {
            String query1 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4) VALUES('-5º ▼', '10','11','14','15');";
            db.execSQL(query1);
            String query2 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('-5º ~ 0º','9','10','11','14','15');";
            db.execSQL(query2);
            String query3 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('0º ~ 5º','7','9','10','12','14');";
            db.execSQL(query3);
            String query4 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('5º ~ 10º', '5','6','8','7','12');";
            db.execSQL(query4);
            String query5 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4) VALUES('10º ~ 15º', '5','6','8','12');";
            db.execSQL(query5);
            String query6 = "INSERT INTO clothes(Temperature, item1, item2, item3) VALUES('15º ~ 20º', '3','4','12');";
            db.execSQL(query6);
            String query7 = "INSERT INTO clothes(Temperature, item1, item2, item3, item4, item5) VALUES('20º ~ 25º', '2','3','4','12','13');";
            db.execSQL(query7);
            String query8 = "INSERT INTO clothes(Temperature, item1, item2, item3) VALUES('25º ~ 30º', '2','12','13');";
            db.execSQL(query8);
            String query9 = "INSERT INTO clothes(Temperature, item1, item2, item3) VALUES('30º ▲', '1','2','13');";
            db.execSQL(query9);
            close();

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
