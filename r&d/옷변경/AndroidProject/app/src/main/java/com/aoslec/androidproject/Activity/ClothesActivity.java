package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.aoslec.androidproject.R;
import com.aoslec.androidproject.ShareBar.ShareBar;
import com.bumptech.glide.Glide;

public class ClothesActivity extends AppCompatActivity {

    ShareBar shareBar = new ShareBar();
    String url = shareBar.sUrl;
    String clo = shareBar.clothesC;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        imageView = findViewById(R.id.img);
        setTitle("옷설정");

        Log.v("ggg",shareBar.clothesC + "?");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v("ggg",shareBar.clothesC + "?");

        Glide.with(ClothesActivity.this)
                .load(url+clo+"item1.png")
                .into(imageView);

    }
}