package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.LinearLayout;

// represents a door
public class Door {
    private ICharacter character;   // character in the door
    private VolumeSound volumeSound;    // represents volume settings for playing sounds
    private Context context;    // game activity context
    private Player player;  // actual playing player
    private int layoutId;
    private int topLayoutId;
    private boolean accepted = false;
    private IDoorObserver doorObserver;
    private TopLayoutsManagement topLayoutsManagement;
    private GameStage gameStage;

    public Door(Context context, Player player, int layoutId, VolumeSound volumeSound, int topLayoutId,
                IDoorObserver doorObserver, TopLayoutsManagement topLayoutsManagement, GameStage gameStage){
        this.character = null;
        this.volumeSound = volumeSound;
        this.context = context;
        this.layoutId = layoutId;
        this.player = player;
        this.topLayoutId = topLayoutId;
        this.doorObserver = doorObserver;
        this.topLayoutsManagement = topLayoutsManagement;
        this.gameStage = gameStage;
    }

    // adds character to the door
    public void addCharacter(){
        ICharacterFactory characterFactory = new CharacterFactory();
        // calculates the chance of generating bad character
        double chanceForBadCharacter = (double)this.gameStage.getActualLevel()/10 + 0.5;
        // chance can't be greater than 90%
        if(chanceForBadCharacter >= 0.9)
            chanceForBadCharacter = 0.9;
        Log.i(null, "Probability for the bad character: " + chanceForBadCharacter);
        ICharacter createdCharacter = characterFactory.createCharacter(this.context, this.player, this, chanceForBadCharacter);
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

    // resets the door
    public void reset(){
        // if a character is inside - remove it
        if(this.character != null){
            this.character.delete();
            this.character = null;
        }
        // resets a door appearance to the default
        LinearLayout layout = (LinearLayout)((Activity)this.context).findViewById(this.layoutId);
        layout.setBackgroundColor(Color.WHITE);
    }

    // go back to the initial state - without character and with not accepted state
    public void hardReset(){
        reset();
        this.accepted = false;
        LinearLayout topLayout = ((Activity)this.context).findViewById(this.topLayoutId);
        // reset top layout according to the door to the initial state
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.RED);
        topLayout.setBackground(border);
    }

    // accept the door - good character passed the door
    public void acceptDoor(){
        // notice a top layout
        this.topLayoutsManagement.setAccepted(this.topLayoutId);
        this.accepted = true;
        // inform observer about the door acceptance
        this.doorObserver.singleDoorAcceptanceDetected();
    }

    public boolean isAccepted(){
        return this.accepted;
    }
}
