package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.aoslec.androidproject.Language.LanguageManager;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

public class IntroActivity extends AppCompatActivity {

    LanguageManager languageManager = new LanguageManager(this);
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        languageManager.updateResource(saveSharedPreferences.getLangMethod(this));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, ExplainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}