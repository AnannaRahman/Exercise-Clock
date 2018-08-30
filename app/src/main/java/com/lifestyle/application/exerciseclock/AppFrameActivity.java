package com.lifestyle.application.exerciseclock;

import android.os.Build;
import android.os.Bundle;

import com.lifestyle.application.exerciseclock.fragment.HistoryListFragment;
import com.lifestyle.application.exerciseclock.interfaces.ITaskOnCompletion;
import com.lifestyle.application.exerciseclock.interfaces.ITaskOnFinish;
import com.lifestyle.application.exerciseclock.utill.Common;
import com.lifestyle.application.exerciseclock.utill.SystemConstants;
import com.github.clans.fab.FloatingActionButton;

import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lifestyle.application.exerciseclock.adapter.ViewPagerAdapter;
import com.lifestyle.application.exerciseclock.fragment.SetUpFragment;


/**
 * Created by Ananna on 10/31/2017.
 */

public class AppFrameActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ITaskOnCompletion mListener;

    public void setCustomEventListener(ITaskOnCompletion eventListener) {
        this.mListener = eventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_frame_layout);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Common.RemoveAllDataFromSharedPref(getApplication(), SystemConstants.WorkOut_Information);
        setSupportActionBar(toolBar);
        setDataViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setDataViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetUpFragment(), "Plan");
        adapter.addFragment(new HistoryListFragment(), "History");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
