package com.paum.bangbang;

import android.content.Context;

import java.util.Random;

public class CharacterFactory implements ICharacterFactory{

    /**
     * Creates a character instance
     * @param chance Probability (0-100) to create a bad character
     * @return Instance of the character
     */
    @Override
    public ICharacter createCharacter(Context context, Player player, Door door, double chance) {
        // creates character
        Random rnd = new Random();
        int ret = rnd.nextInt(100) + 1;
        if(ret < chance * 100){
            // return bad character
            return new BadCharacter(context, player, door);
        }
        else{
            // return good character
            return new GoodCharacter(context, player, door);
        }
    }
}
