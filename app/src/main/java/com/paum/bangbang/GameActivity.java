package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameStage gameStage;
    private Player player;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);
        Resources res = getResources();
        // initialize player with initial values from resources
        this.player = new Player(res.getInteger(R.integer.playerInitialLives), res.getInteger(R.integer.scoreForKillBadCharacter));
        // initialize game stage
        this.gameStage = new GameStage(this, this.player);
        // attach lose one life observer - activates when player lose life
        this.player.attachLoseOneLifeObserver(new LoseOneLifeObserver(this.gameStage));
        // attach player lives observer - activates when player lose all lives
        this.player.attach(new PlayerLivesObserver(this, this.gameStage));
        // initialize class responsible for playing game sounds
        GameSoundPlayer.initialize(this);

        // Left side of the screen
        LinearLayout leftLayout = findViewById(R.id.layoutLeft);
        leftLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(0);
            }
            return true;
        });

        // Middle side of the screen
        LinearLayout middleLayout = findViewById(R.id.layoutMiddle);
        middleLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(1);
            }
            return true;
        });

        // Right side of the screen
        LinearLayout rightLayout = findViewById(R.id.layoutRight);
        rightLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(2);
            }
            return true;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.gameStage.finish();
    }
}