package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

public class GoodCharacter implements ICharacter{
    private Context context;
    private int layoutId;
    private static final String TAG = "GoodCharacter";

    public GoodCharacter(Context context, int layoutId){

        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public void appear(float leftVolumeSound, float rightVolumeSound) {
        // set background color of the layout where the character appeared to the blue
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.BLUE);
        // play sound
        GameSoundPlayer.getInstance(this.context).goodPlayerAppeared(leftVolumeSound, rightVolumeSound);
    }

    @Override
    public void getShot(Player player) {
        // wrong shot - take a player's live
        player.takeLive();
        // reset the color
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.WHITE);
        Log.i(TAG, "Good character was shot");
    }

    @Override
    public String toString() {
        return "Good Character";
    }
}
