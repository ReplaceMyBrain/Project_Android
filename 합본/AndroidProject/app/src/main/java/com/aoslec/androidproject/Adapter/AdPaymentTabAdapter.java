package com.aoslec.androidproject.Adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aoslec.androidproject.Fragment.AdPaymentCancelFragment;
import com.aoslec.androidproject.Fragment.AdPaymentHistoryFragment;
import com.aoslec.androidproject.Fragment.AdPaymentNowFragment;
import com.aoslec.androidproject.Fragment.AdPaymentWaitFragment;

/**
 * Created by biso on 2021/06/23.
 */
public class AdPaymentTabAdapter extends FragmentPagerAdapter {

    int tabCount;

    public AdPaymentTabAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
        Log.v("Message", "public TabPagerAdapter/TabPagerAdapter");
        this.tabCount = behavior;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("Message", "public Fragment getItem/TabPagerAdapter");

        switch (position) {
            case 1:
                return new AdPaymentWaitFragment();
            case 2:
                return new AdPaymentHistoryFragment();
            case 3:
                return new AdPaymentCancelFragment();

        }
        return new AdPaymentNowFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
