package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aoslec.androidproject.R;

public class ClothesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        setTitle("옷설정");
    }
}