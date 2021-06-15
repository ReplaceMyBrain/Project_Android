package com.aoslec.androidproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aoslec.androidproject.Fragment.Login_SignInFragment;
import com.aoslec.androidproject.Fragment.Login_SignUpFragment;

public class LoginTabAdapter extends FragmentStateAdapter {


    public LoginTabAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1:
                return new Login_SignUpFragment();
        }
        return new Login_SignInFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
