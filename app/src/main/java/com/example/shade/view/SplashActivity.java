package com.example.shade.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.R;
import com.example.shade.view.userActivity.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        icon = findViewById(R.id.splash_icon);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.spin);
        icon.setAnimation(anim);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

