package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

// represents a character
public class BadCharacter implements ICharacter{
    private Context context;
    private int layoutId;
    private static final String TAG = "BadCharacter";

    public BadCharacter(Context context, int layoutId){

        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public void appear(float leftVolumeSound, float rightVolumeSound) {
        // set background color of the layout where the character appeared to the red
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.RED);
        // play sound
        GameSoundPlayer.getInstance(this.context).badPlayerAppeared(leftVolumeSound, rightVolumeSound);
    }

    @Override
    public void getShot(Player player) {
        // add score for the player
        player.addScore();
        // reset the color
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.WHITE);
        Log.i(TAG, "Bad character was shot");
    }

    @Override
    public String toString() {
        return "Bad Character";
    }
}
