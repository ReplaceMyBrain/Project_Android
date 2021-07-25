package com.aoslec.androidproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aoslec.androidproject.Fragment.Admin_ApproveFragment;
import com.aoslec.androidproject.Fragment.Admin_RequestFragment;

public class AdminTabAdapter extends FragmentStateAdapter {

    public AdminTabAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Admin_RequestFragment();
        }
        return new Admin_ApproveFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
