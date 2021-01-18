package com.paum.bangbang;

// interface for characters
public interface ICharacter {
    // handle appearance of the character
    void appear(float leftVolumeSound, float rightVolumeSound);
    // handle the shot
    void getShot(Player player);
}
