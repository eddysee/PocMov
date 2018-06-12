package com.gilandeddy.pocketmovie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Gilbert & Eddy
 * This class MainPagerAdapter
 *
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    /**
     *
     * @param context
     * @param fm
     */
    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecentFragment();
            case 1:
                return new PocketFragment();
            default:
                return null;

        }

    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Recent Movies";
            case 1:
                return "Pocket Movies";
            default:
                return null;

        }
    }
}
