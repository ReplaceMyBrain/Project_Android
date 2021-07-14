package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aoslec.androidproject.Adapter.AdPaymentTabAdapter;
import com.aoslec.androidproject.R;
import com.google.android.material.tabs.TabLayout;

public class AdPaymentActicity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    int tabindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_payment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tabLayout = findViewById(R.id.tablayout_ad_payment);
        tabLayout.addTab(tabLayout.newTab().setText("게시중 광고"));
        tabLayout.addTab(tabLayout.newTab().setText("대기중"));
        tabLayout.addTab(tabLayout.newTab().setText("과거내역"));
        tabLayout.addTab(tabLayout.newTab().setText("취소 내역"));

        tabSetting();

        Intent intent = getIntent();
        tabindex = intent.getIntExtra("tabindex",0);

        viewPager = findViewById(R.id.pager_ad_payment);

        PagerAdapter pagerAdapter = new AdPaymentTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("Message","public void onTabSelected/MainActivity");
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void tabSetting() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.getTabAt(tabindex).select();
                    }
                }, 10);
    }
}