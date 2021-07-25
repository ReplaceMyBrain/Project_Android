package com.aoslec.androidproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.Language.LanguageManager;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import com.aoslec.androidproject.R;

public class IntroActivity extends AppCompatActivity implements AutoPermissionsListener{

    Handler handler = new Handler();
    LanguageManager languageManager = new LanguageManager(this);
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AutoPermissions.Companion.loadAllPermissions(this, 101);
        languageManager.updateResource(saveSharedPreferences.getLangMethod(this));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        handler.postDelayed(new Runnable() {
            public void run() {
                if (SaveSharedPreferences.getFirstVisitUser(getApplicationContext()).equals("y")) {
                    Intent intent = new Intent(getApplicationContext(), ExplainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }
}