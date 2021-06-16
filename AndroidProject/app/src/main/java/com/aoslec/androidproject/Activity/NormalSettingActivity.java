package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aoslec.androidproject.R;

public class NormalSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_setting);

        setTitle("일반설정");
    }
}