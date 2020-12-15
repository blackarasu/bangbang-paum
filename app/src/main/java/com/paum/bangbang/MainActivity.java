package com.paum.bangbang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            public void onSwipeLeft(){
                Intent intent = new Intent(MainActivity.this, GloryActivity.class);
                startActivity(intent);
            }
            public void onSwipeTop(){
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
            public void onSwipeBottom(){
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}