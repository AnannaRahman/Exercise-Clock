package com.lifestyle.application.exerciseclock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ananna on 10/28/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> tabTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {

        return fragmentArrayList.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        tabTitleList.add(title);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }

}
