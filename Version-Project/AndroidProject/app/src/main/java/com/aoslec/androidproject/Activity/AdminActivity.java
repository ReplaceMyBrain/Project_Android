package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.aoslec.androidproject.Adapter.AdminTabAdapter;
import com.aoslec.androidproject.R;
import com.google.android.material.tabs.TabLayout;

public class AdminActivity extends AppCompatActivity {

    TabLayout tableLayout;
    ViewPager2 viewPager2;
    AdminTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setTitle(getResources().getString(R.string.admin_title));

        tableLayout = findViewById(R.id.admin_tabLayout);
        viewPager2 = findViewById(R.id.admin_viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new AdminTabAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tableLayout.selectTab(tableLayout.getTabAt(position));
            }
        });

    }
}