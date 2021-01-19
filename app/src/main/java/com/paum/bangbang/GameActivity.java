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
        this.gameStage = new GameStage(this);
        Resources res = getResources();
        int playerInitLives = res.getInteger(R.integer.playerInitialLives);
        int playerScoreForShot = res.getInteger(R.integer.scoreForKillBadCharacter);
        this.player = new Player(playerInitLives, playerScoreForShot);
        PlayerLivesObserver livesObserver = new PlayerLivesObserver(this);
        this.player.attach(livesObserver);
        GameSoundPlayer.initialize(this);

        /*View view = findViewById(android.R.id.content);
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });*/

        // Left side of the screen
        LinearLayout leftLayout = findViewById(R.id.layoutLeft);
        leftLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(this.player, 0);
            }
            return true;
        });

        // Middle side of the screen
        LinearLayout middleLayout = findViewById(R.id.layoutMiddle);
        middleLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(this.player, 1);
            }
            return true;
        });

        // Right side of the screen
        LinearLayout rightLayout = findViewById(R.id.layoutRight);
        rightLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                gameStage.playerShoot(this.player, 2);
            }
            return true;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.gameStage.stopGeneratingCharacters();
        //in the future save player's score in a db
    }
}