package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameStage gameStage;
    private Player player;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn on a fullscreen mode
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            getWindow().getInsetsController().setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE);
        }
        else{
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);
        Resources res = getResources();
        // initialize player with initial values from resources
        this.player = new Player(res.getInteger(R.integer.playerInitialLives), res.getInteger(R.integer.scoreForKillBadCharacter));
        // initialize game stage
        this.gameStage = new GameStage(this, this.player, new TopLayoutsManagement(this));
        // attach lose one life observer - activates when player lose life
        this.player.attachLoseOneLifeObserver(new LoseOneLifeObserver(this.gameStage));
        // attach player lives observer - activates when player lose all lives
        this.player.attach(new PlayerLivesObserver(this, this.gameStage));
        // initialize class responsible for playing game sounds
        GameSoundPlayer.initialize(this);

        // Left side of the screen
        LinearLayout leftLayout = findViewById(R.id.layoutLeft);
        leftLayout.setOnTouchListener(new OnGestureListener(GameActivity.this) {
            public void onTapUp() {
                gameStage.playerShoot(0);
            }

            public void onSwipeRight() {
                swipeRight();
            }

            public void onSwipeLeft(){
                swipeLeft();
            }
        });

        // Middle side of the screen
        LinearLayout middleLayout = findViewById(R.id.layoutMiddle);
        middleLayout.setOnTouchListener(new OnGestureListener(GameActivity.this) {
            public void onTapUp() {
                gameStage.playerShoot(1);
            }

            public void onSwipeRight() { swipeRight(); }

            public void onSwipeLeft(){
                swipeLeft();
            }
        });

        // Right side of the screen
        LinearLayout rightLayout = findViewById(R.id.layoutRight);
        rightLayout.setOnTouchListener(new OnGestureListener(GameActivity.this) {
            public void onTapUp() {
                gameStage.playerShoot(2);
            }

            public void onSwipeRight() {
                swipeRight();
            }

            public void onSwipeLeft(){
                swipeLeft();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.gameStage.finish();
    }

    // handle swipe right
    private void swipeRight(){
        this.gameStage.moveToPreviousDoors();
    }

    // handle swipe left
    private void swipeLeft(){
        this.gameStage.moveToNextDoors();
    }
}