package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

// represents a door
public class Door {
    private ICharacter character;   // character in the door
    private VolumeSound volumeSound;    // represents volume settings for playing sounds
    private Context context;    // game activity context
    private GameSoundPlayer gameSoundPlayer;
    private Player player;  // actual playing player
    private int layoutId;

    public Door(Context context, Player player, int layoutId, VolumeSound volumeSound){
        this.character = null;
        this.volumeSound = volumeSound;
        this.context = context;
        this.layoutId = layoutId;
        this.player = player;
    }

    // adds character to the door
    public void addCharacter(){
        ICharacterFactory characterFactory = new CharacterFactory();
        ICharacter createdCharacter = characterFactory.createCharacter(this.context, this.player, this, 0.5);
        this.character = createdCharacter;
        this.character.appear();
        Log.i(null, "Created " + createdCharacter);
    }

    // display a character with the specified color
    public void showCharacter(int color){
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(color);
    }

    // returns true if the door is empty (no character inside)
    public boolean getState(){
        if(this.character == null)
            return true;
        return false;
    }

    // player shot the door
    public void shoot(){
        GameSoundPlayer.getInstance(this.context).playerShot(getVolumeSound());
        if(this.character != null){
            this.character.getShot();
        }
    }

    public VolumeSound getVolumeSound(){
        return this.volumeSound;
    }

    // reset a door to a initial state - without a character
    public void reset(){
        if(this.character != null){
            this.character.delete();
            this.character = null;
        }
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.WHITE);
    }
}
