package com.example.mvopo.tsekapp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import com.example.mvopo.tsekapp.Fragments.MessageThreadFragment;
import com.example.mvopo.tsekapp.Model.User;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by mvopo on 1/30/2018.
 */

public class ChatActivity extends AppCompatActivity {

    public static Toolbar toolbar;
    public static User user;

    MessageThreadFragment mtf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = getIntent().getExtras();
        user = b.getParcelable("user");

        getSupportActionBar().setTitle(b.getString("messageTo"));

        mtf = new MessageThreadFragment();
        mtf.setArguments(b);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, mtf).commit();

        FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabMenu.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
            mtf.removeRegisteredListener();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
