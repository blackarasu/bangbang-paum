package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GloryActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glory);

        View view = findViewById(android.R.id.content);
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                @SuppressLint("ClickableViewAccessibility") Intent intent = new Intent(GloryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });
    }
}