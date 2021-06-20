package com.aoslec.androidproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aoslec.androidproject.Fragment.Main_FavoriteFragment;
import com.aoslec.androidproject.Fragment.Main_SettingFragment;
import com.aoslec.androidproject.Fragment.Main_WeatherFragment;

public class MainTabAdapter extends FragmentStateAdapter {

    public MainTabAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new Main_FavoriteFragment();
            case 2:
                return new Main_SettingFragment();
        }
        return new Main_WeatherFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
