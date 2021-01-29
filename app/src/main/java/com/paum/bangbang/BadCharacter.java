package com.paum.bangbang;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;


// represents a bad character
public class BadCharacter implements ICharacter {
    // context of the game activity
    private final Context context;
    // timer for character time
    private CountDownTimer characterTimer;
    // actual playing player
    private final Player player;
    // the door in which character appeared
    private final Door door;
    private final int existenceTime = 2000;
    private static final String TAG = "BadCharacter";

    public BadCharacter(Context context, Player player, Door door) {
        this.context = context;
        this.player = player;
        this.door = door;
    }

    @Override
    public void appear() {
        // display character with the red color
        this.door.showCharacter(Color.RED);
        // play sound
        GameSoundPlayer.getInstance(this.context).badCharacterAppeared(this.door.getVolumeSound());

        // time of the character
        characterTimer = new CountDownTimer(this.existenceTime, this.existenceTime) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                timeOut();
            }
        }.start();
    }

    @Override
    public void getShot() {
        // add score for the player
        this.player.addScore();
        // delete character from the door
        this.door.reset();
        GameSoundPlayer.getInstance(this.context).badCharacterKilled(this.door.getVolumeSound());
        Log.i(TAG, "Bad character was shot. Player's score: " + this.player.getScore());
    }

    // timeout - character was not shot
    private void timeOut() {
        GameSoundPlayer.getInstance(this.context).badCharacterNotKilled(this.door.getVolumeSound());
        // delete character from the door
        this.door.reset();
        // take player's live - bad character was not shot
        this.player.takeLive();
        Log.i(TAG, "Bad character was not shot.");
    }

    // delete character from the game
    public void delete() {
        this.characterTimer.cancel();
    }

    @Override
    public String toString() {
        return "Bad Character";
    }
}
