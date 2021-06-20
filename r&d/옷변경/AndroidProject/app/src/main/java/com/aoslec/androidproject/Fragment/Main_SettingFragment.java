package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aoslec.androidproject.Activity.AdActivity;
import com.aoslec.androidproject.Activity.AdminActivity;
import com.aoslec.androidproject.Activity.ClothesActivity;
import com.aoslec.androidproject.Activity.LoginActivity;
import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Activity.NormalSettingActivity;
import com.aoslec.androidproject.R;


public class Main_SettingFragment extends Fragment {

    LinearLayout login, setting, clothes, ad, admin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);

        login = view.findViewById(R.id.setting_login);
        setting = view.findViewById(R.id.setting_normalSetting);
        clothes = view.findViewById(R.id.setting_clothes);
        ad = view.findViewById(R.id.setting_ad);
        admin = view.findViewById(R.id.setting_admin);

        login.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        clothes.setOnClickListener(onClickListener);
        ad.setOnClickListener(onClickListener);
        admin.setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //액션바 타이틀 변경
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("설정");
        }
    }

   View.OnClickListener onClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           switch (v.getId()){

               case R.id.setting_login:
                   Intent intent = new Intent(getActivity(), LoginActivity.class);
                   startActivity(intent);
                   break;

               case R.id.setting_normalSetting:
                   Intent intent1 = new Intent(getActivity(), NormalSettingActivity.class);
                   startActivity(intent1);
                   break;

               case R.id.setting_clothes:
                   Intent intent2 = new Intent(getActivity(), ClothesActivity.class);
                   startActivity(intent2);
                   break;

               case R.id.setting_ad:
                   Intent intent3 = new Intent(getActivity(), AdActivity.class);
                   startActivity(intent3);
                   break;

               case R.id.setting_admin:
                   Intent intent4 = new Intent(getActivity(), AdminActivity.class);
                   startActivity(intent4);
                   break;

           }
       }
   };

}//