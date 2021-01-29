package com.paum.bangbang;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;


public class GoodCharacter implements ICharacter {
    // context of the game activity
    private final Context context;
    // timer for character time
    private CountDownTimer characterTimer;
    // actual playing player
    private final Player player;
    // the door in which character appeared
    private final Door door;
    private final int existenceTime = 2000;
    private static final String TAG = "GoodCharacter";

    public GoodCharacter(Context context, Player player, Door door) {
        this.context = context;
        this.player = player;
        this.door = door;
    }

    @Override
    public void appear() {
        // display character with the blue color
        this.door.showCharacter(Color.BLUE);
        // play sound
        GameSoundPlayer.getInstance(this.context).goodCharacterAppeared(this.door.getVolumeSound());

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
        // wrong shot - take a player's live
        this.player.takeLive();
        // remove character from the door
        this.door.reset();
        GameSoundPlayer.getInstance(this.context).wrongShot(this.door.getVolumeSound());
        Log.i(TAG, "Good character was shot. Player's lives: " + this.player.getLives());
    }

    // timeout - character was not shot
    private void timeOut() {
        GameSoundPlayer.getInstance(this.context).goodCharacterNotKilled(this.door.getVolumeSound());
        // remove character from the door
        this.door.reset();
        // accept the door
        this.door.acceptDoor();

        Log.i(TAG, "Good character was not shot.");
    }

    // delete character from the game
    public void delete() {
        this.characterTimer.cancel();
    }

    @Override
    public String toString() {
        return "Good Character";
    }
}
