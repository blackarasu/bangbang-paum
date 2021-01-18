package com.paum.bangbang;

import android.content.Context;

import java.util.Random;

public class CharacterFactory implements ICharacterFactory{

    /**
     * Creates a character instance
     * @param chance Probability (0-100) to create the good character
     * @return Instance of the character
     */
    @Override
    public ICharacter createCharacter(Context context, int layoutId, double chance) {
        // creates character
        Random rnd = new Random();
        int ret = rnd.nextInt(100) + 1;
        if(ret < chance * 100){
            // return good character
            return new BadCharacter(context, layoutId);
        }
        else{
            // return bad character
            return new GoodCharacter(context, layoutId);
        }
    }
}
