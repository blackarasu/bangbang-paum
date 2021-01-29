package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

// observes when a player lose all lives
public class PlayerLivesObserver implements IObserver{

    Context context;
    GameStage gameStage;

    public PlayerLivesObserver(Context context, GameStage gameStage){
        this.context = context;
        this.gameStage = gameStage;
    }

    // action to be taken when a player has no more lives
    @Override
    public void update() {
        // end a game
        this.gameStage.finish();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.lostgame2);
        mediaPlayer.start();
        // play a sound
        mediaPlayer.setOnCompletionListener(mp -> {
            // after the end of the game - closes the activity
            ((Activity)this.context).finish();
        });

    }
}
