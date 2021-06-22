package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.aoslec.androidproject.Adapter.ClothesSettingAdapter;
import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.Bean.WeatherBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SQLite.ClothesSQLite;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ClothesActivity extends AppCompatActivity {

    ArrayList<ClothesBean> clothes = new ArrayList<>();
    ClothesSettingAdapter adapter;
    ListView listView;
    ClothesSQLite clothesSQLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        setTitle(getResources().getString(R.string.clothes_title));

        clothesSQLite = new ClothesSQLite(ClothesActivity.this);

        listView = findViewById(R.id.clothes_listView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLite();
    }

    private void SQLite() {
        SQLiteDatabase DB;

        Log.v("ggg","SQLite");
        try {
            clothes.clear();
            DB=clothesSQLite.getReadableDatabase();
            String query = "Select Temperature, item1, item2, item3, item4, item5 FROM clothes;";
            Cursor cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()){
                String temperature = cursor.getString(0);
                String item1 = cursor.getString(1);
                String item2 = cursor.getString(2);
                String item3 = cursor.getString(3);
                String item4 = cursor.getString(4);
                String item5 = cursor.getString(5);

                Log.v("ggg","SQLite" + temperature + item1 + item2);

                ClothesBean clothesBean = new ClothesBean(temperature,item1,item2,item3,item4,item5);
                clothes.add(clothesBean);
            }
            cursor.close();
            clothesSQLite.close();

        }catch (Exception e){
            e.printStackTrace();
            Log.v("ggg","불러오기 실패함");
        }

        try{
            Log.v("ggg","아답터");
            adapter = new ClothesSettingAdapter(ClothesActivity.this,R.layout.listview_clothes, clothes);
            listView.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}