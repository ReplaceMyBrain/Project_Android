package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.aoslec.androidproject.Activity.ClothesActivity;
import com.aoslec.androidproject.Activity.NormalSettingActivity;
import com.aoslec.androidproject.R;


public class Main_SettingFragment extends Fragment {

    LinearLayout setting, clothes;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);

        setting = view.findViewById(R.id.setting_normalSetting);
        clothes = view.findViewById(R.id.setting_clothes);


        setting.setOnClickListener(onClickListener);
        clothes.setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //액션바 타이틀 변경
        FragmentActivity activity = getActivity();
        if (activity != null) {
//            MainActivity.setActionBarTitle("설정");
            // getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Setting");
        }

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.setting_normalSetting:
                    Intent intent1 = new Intent(getActivity(), NormalSettingActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.setting_clothes:
                    Intent intent2 = new Intent(getActivity(), ClothesActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };


}