package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.aoslec.androidproject.Activity.AdminActivity;
import com.aoslec.androidproject.Activity.ClothesActivity;
import com.aoslec.androidproject.Activity.MyPageActivity;
import com.aoslec.androidproject.Activity.NormalSettingActivity;
import com.aoslec.androidproject.Activity.SignInActivity;
import com.aoslec.androidproject.AdActivity.AdActivity;
import com.aoslec.androidproject.AdActivity.AdPaymentActicity;
import com.aoslec.androidproject.Bean.Ad_PaymentBean;
import com.aoslec.androidproject.NetworkTask.AdPayment_NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class Main_SettingFragment extends Fragment {

    int tabindex;
    FrameLayout layout_icon_list;
    ImageView profile, defaultprofile;
    TextView name, tv_wait, tv_now, tv_history, tv_cancel;
    LinearLayout layout_now, layout_wait, layout_history, layout_cancel;
    LinearLayout login, setting, clothes, ad, admin, profile_layout;
    ArrayList<Ad_PaymentBean> ad_paymentBeans;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);

        layout_icon_list = view.findViewById(R.id.layout_icon_list);
        profile = view.findViewById(R.id.iv_icon_setting);
        name = view.findViewById(R.id.tv_name_setting);
        profile_layout = view.findViewById(R.id.layout_login_setting);
        defaultprofile = view.findViewById(R.id.iv_icon_d_setting);

        tv_wait = view.findViewById(R.id.tv_count_wait_setting);
        tv_now = view.findViewById(R.id.tv_count_now_setting);
        tv_cancel =  view.findViewById(R.id.tv_count_cancel_setting);
        tv_history = view.findViewById(R.id.tv_count_history_setting);

        layout_cancel = view.findViewById(R.id.ll_cancel_setting);
        layout_history = view.findViewById(R.id.ll_history_setting);
        layout_now = view.findViewById(R.id.ll_now_setting);
        layout_wait = view.findViewById(R.id.ll_wait_setting);

        login = view.findViewById(R.id.setting_login);
        setting = view.findViewById(R.id.setting_normalSetting);
        clothes = view.findViewById(R.id.setting_clothes);
        ad = view.findViewById(R.id.setting_ad);
        admin = view.findViewById(R.id.setting_admin);

        if (SaveSharedPreferences.getPrefIsLogin(getContext()).equals("n")) {
            profile_layout.setVisibility(View.GONE);
            profile.setVisibility(View.INVISIBLE);

        }else {
            if (SaveSharedPreferences.getPrefImage(getContext()) == null || SaveSharedPreferences.getPrefImage(getContext()).equals("")) {
                login.setVisibility(View.GONE);
                profile.setVisibility(View.INVISIBLE);
                defaultprofile.setBackground(new ShapeDrawable(new OvalShape()));
                defaultprofile.setClipToOutline(true);
                defaultprofile.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
            }else {
                login.setVisibility(View.GONE);
                defaultprofile.setVisibility(View.INVISIBLE);
                profile.setBackground(new ShapeDrawable(new OvalShape()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    profile.setClipToOutline(true);
                    Glide.with(this).load(SaveSharedPreferences.getPrefImage(getContext())).into(profile);
                }

            }

        }



        ad_countAction();
        login.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        clothes.setOnClickListener(onClickListener);
        ad.setOnClickListener(onClickListener);
        admin.setOnClickListener(onClickListener);

        layout_icon_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });

//        email = view.findViewById(R.id.tv_email_setting);

        //profile 이미지를 동그랗게



        name.setText(SaveSharedPreferences.getPrefName(getContext())+">");

        layout_wait.setOnClickListener(layoutClickAction);
        layout_now.setOnClickListener(layoutClickAction);
        layout_history.setOnClickListener(layoutClickAction);
        layout_cancel.setOnClickListener(layoutClickAction);

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
                case R.id.setting_login:
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
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

    private void ad_countAction () {
        String urlAddr = ShareVar.sUrl + "select_ad_count.jsp?email=" + SaveSharedPreferences.getPrefEmail(getContext());
        Log.v("Message", urlAddr);
        try {
            AdPayment_NetworkTask adPayment_networkTask = new AdPayment_NetworkTask(getContext(), urlAddr, "select_count");
            Object obj = adPayment_networkTask.execute().get();
            ad_paymentBeans = (ArrayList<Ad_PaymentBean>) obj;

            tv_now.setText(ad_paymentBeans.get(0).getAd_count());
            tv_wait.setText(ad_paymentBeans.get(1).getAd_count());
            tv_history.setText(ad_paymentBeans.get(2).getAd_count());
            tv_cancel.setText(ad_paymentBeans.get(3).getAd_count());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    View.OnClickListener layoutClickAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll_now_setting:
                    tabindex = 0;
                    break;
                case R.id.ll_wait_setting:
                    tabindex = 1;
                    break;
                case R.id.ll_history_setting:
                    tabindex = 2;
                    break;
                case R.id.ll_cancel_setting:
                    tabindex = 3;
                    break;
            }
            Intent intent = new Intent(getContext(), AdPaymentActicity.class);
            intent.putExtra("tabindex", tabindex);
            startActivity(intent);
        }
    };
}