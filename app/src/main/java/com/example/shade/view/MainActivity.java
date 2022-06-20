package com.example.shade.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shade.R;
import com.example.shade.fragment.MyPageFragment;
import com.example.shade.fragment.MySchoolFragment;
import com.example.shade.fragment.MyWriteFragment;
import com.example.shade.fragment.TimeLineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigationView);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new TimeLineFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()){
                    case R.id.navigation_timeline: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TimeLineFragment()).commit();
                        break;
                    case R.id.navigation_myschool: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MySchoolFragment()).commit();
                        break;
                    case R.id.navigation_mywrite: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MyWriteFragment()).commit();
                        break;
                    case R.id.navigation_mypage: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MyPageFragment()).commit();
                        break;
                }
                return true;
            }
        });


    }
}