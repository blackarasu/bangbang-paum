package com.paum.bangbang;

import android.content.Context;
import android.util.Log;

// represents a door
public class Door {
    private ICharacter character;   // character in the door
    private float leftVolumeSound;
    private float rightVolumeSound;
    private Context context;
    private GameSoundPlayer gameSoundPlayer;
    private int layoutId;

    public Door(Context context, int layoutId, float leftVolumeSound, float rightVolumeSound){
        this.character = null;
        this.leftVolumeSound = leftVolumeSound;
        this.rightVolumeSound = rightVolumeSound;
        this.context = context;
        this.layoutId = layoutId;
    }

    // adds character to the door
    public void addCharacter(){
        ICharacterFactory characterFactory = new CharacterFactory();
        ICharacter createdCharacter = characterFactory.createCharacter(this.context, this.layoutId, 0.5);
        this.character = createdCharacter;
        this.character.appear(this.leftVolumeSound, this.rightVolumeSound);
        Log.i(null, "Created " + createdCharacter);
    }

    // returns true if the door is empty (no character inside)
    public boolean getState(){
        if(this.character == null)
            return true;
        return false;
    }

    // player shot the door
    public void shoot(Player player){
        if(this.character != null){
            this.character.getShot(player);
            this.character = null;
        }
    }
}
