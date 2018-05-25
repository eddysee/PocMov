package com.gilandeddy.pocketmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.view.Menu;
import android.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {

    private ActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main_tab,menu);
        MenuItem sharePocket = menu.findItem(R.id.action_share);
        shareActionProvider = MenuItemCompat.getActionProvider(sharePocket);
        sharePocket.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem sharePocket) {
                String message = new String("Wanna watch");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,message);
                startActivity(shareIntent);


                return true;
            }
        });



        return true;
    }

}

