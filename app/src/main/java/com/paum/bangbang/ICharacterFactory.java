package com.paum.bangbang;

import android.content.Context;

public interface ICharacterFactory {
    ICharacter createCharacter(Context context, int layoutId, double chance);
}
