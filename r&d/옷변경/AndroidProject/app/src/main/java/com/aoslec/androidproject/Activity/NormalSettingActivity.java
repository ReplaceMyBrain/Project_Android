package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aoslec.androidproject.R;
import com.aoslec.androidproject.ShareBar.ShareBar;

public class NormalSettingActivity extends AppCompatActivity {

    Button img1, img2;
    ShareBar shareBar = new ShareBar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_setting);
        setTitle("일반설정");

        img1 = findViewById(R.id.btn_1);
        img2 = findViewById(R.id.btn_2);

        img1.setOnClickListener(click);
        img2.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1:
                    shareBar.clothesC = "img1/";
                    Log.v("ggg",shareBar.clothesC + "?");
                    break;
                    
                case R.id.btn_2:
                    shareBar.clothesC = "img2/";
                    Log.v("ggg",shareBar.clothesC + "?");
                    break;
            }
        }
    };
}//=====