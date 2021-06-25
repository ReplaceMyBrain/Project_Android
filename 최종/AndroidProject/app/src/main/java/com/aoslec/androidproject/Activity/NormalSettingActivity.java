package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aoslec.androidproject.Language.LanguageManager;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;

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

        //버튼
        kor.setOnClickListener(language);
        eng.setOnClickListener(language);
        cNone.setOnClickListener(clothesColor);
        cColor.setOnClickListener(clothesColor);

        setTitle(getResources().getString(R.string.normal_tittle));


    }

    @Override
    protected void onResume() {
        super.onResume();
        textColor();
    }

    //설정한 색 설정
    private void textColor() {

        if(saveSharedPreferences.getLangMethod(NormalSettingActivity.this).equals("ko")){
            kor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
            eng.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
        }else if(saveSharedPreferences.getLangMethod(NormalSettingActivity.this).equals("en")){
            kor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
            eng.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
        }

        if(saveSharedPreferences.getClothesColor(NormalSettingActivity.this).equals("color/")){
            cNone.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
            cColor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
        }else if(saveSharedPreferences.getClothesColor(NormalSettingActivity.this).equals("none/")){
            cNone.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
            cColor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
        }

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

    //옷 테마설정
    View.OnClickListener clothesColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.NS_cNone:
                    saveSharedPreferences.setClothesColor(NormalSettingActivity.this, "none/");
//                    cNone.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
//                    cColor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
                    break;

                case R.id.NS_cColor:
                    saveSharedPreferences.setClothesColor(NormalSettingActivity.this, "color/");
//                    cNone.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.black));
//                    cColor.setTextColor(ContextCompat.getColor(NormalSettingActivity.this, R.color.color6));
                    break;

            }
            Intent intent = new Intent(NormalSettingActivity.this, MainActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);

        }
    };

}