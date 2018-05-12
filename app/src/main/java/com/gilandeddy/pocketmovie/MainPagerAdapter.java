package com.gilandeddy.pocketmovie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gilbert on 5/12/18.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new RecentFragment();
            case 1 :
                return new PocketFragment();
                default: return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Recent Movies";
            case 1:
                return "Pocket Movies";
                default: return null;

        }
    }
}
