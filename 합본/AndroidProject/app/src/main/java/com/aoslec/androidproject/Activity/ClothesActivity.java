package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.aoslec.androidproject.Adapter.ClothesSettingAdapter;
import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SQLite.ClothesSQLite;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

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

                ClothesBean clothesBean = new ClothesBean(temperature,item1,item2,item3,item4,item5);
                clothes.add(clothesBean);
            }
            cursor.close();
            clothesSQLite.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            adapter = new ClothesSettingAdapter(ClothesActivity.this,R.layout.listview_clothes, clothes);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(onItemClickListener);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        Intent intent = null;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.v("ggg", "onItemClick 시작");

            intent = new Intent(ClothesActivity.this, ClothesChoiceActivity.class);

            try {
                intent.putExtra("temp", clothes.get(position).getTemperature());
                intent.putExtra("item1", clothes.get(position).getItem1());
                intent.putExtra("item2", clothes.get(position).getItem2());
                intent.putExtra("item3", clothes.get(position).getItem3());
                intent.putExtra("item4", clothes.get(position).getItem4());
                intent.putExtra("item5", clothes.get(position).getItem5());
                startActivity(intent);

                Log.v("ggg", "성공");
            }catch (Exception e){
                e.printStackTrace();
                Log.v("ggg", "실패함");

            }

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0,0);

    }
}//
