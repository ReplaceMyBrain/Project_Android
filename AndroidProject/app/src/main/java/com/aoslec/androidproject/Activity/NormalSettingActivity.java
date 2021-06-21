package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoslec.androidproject.Language.LanguageManager;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

public class NormalSettingActivity extends AppCompatActivity {

    TextView kor,eng,cNone,cColor;

    LanguageManager languageManager = new LanguageManager(this);
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_setting);

        kor=findViewById(R.id.NS_kor);
        eng=findViewById(R.id.NS_eng);

        cNone = findViewById(R.id.NS_cNone);
        cColor = findViewById(R.id.NS_cColor);

        kor.setOnClickListener(language);
        eng.setOnClickListener(language);
        cNone.setOnClickListener(clothesColor);
        cColor.setOnClickListener(clothesColor);

        setTitle(getResources().getString(R.string.normal_tittle));
    }

    //언어설정

    View.OnClickListener language = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.NS_kor:
                    languageManager.updateResource("ko");
                    saveSharedPreferences.setLangMethod(NormalSettingActivity.this,"ko");
                    break;

                case R.id.NS_eng:
                    languageManager.updateResource("en");
                    saveSharedPreferences.setLangMethod(NormalSettingActivity.this,"en");
                    break;
            }
            //기록삭제하여 처음으로 돌아가는 초기화 작업
            Intent intent = new Intent(NormalSettingActivity.this, IntroActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);

        }
    };

    View.OnClickListener clothesColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.NS_cNone:
                    saveSharedPreferences.setClothesColor(NormalSettingActivity.this, "none/");
                    break;

                case R.id.NS_cColor:
                    saveSharedPreferences.setClothesColor(NormalSettingActivity.this, "color/");
                    break;

            }
        }
    };

}