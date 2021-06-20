package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aoslec.androidproject.R;

public class AdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        setTitle("광고요청");
    }
}