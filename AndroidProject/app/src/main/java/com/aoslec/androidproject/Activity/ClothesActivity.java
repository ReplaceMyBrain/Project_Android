package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;


import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;
import com.bumptech.glide.Glide;

public class ClothesActivity extends AppCompatActivity {

    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    String url,clothesColor;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        setTitle(getResources().getString(R.string.clothes_title));

        url = saveSharedPreferences.getUrl(this);
        clothesColor = saveSharedPreferences.getClothesColor(this);

        imageView = findViewById(R.id.clothes_img);

        Glide.with(ClothesActivity.this)
                .load(url+clothesColor+"item1.png")
                .into(imageView);

    }
}