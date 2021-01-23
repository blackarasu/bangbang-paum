package com.paum.bangbang;

public class VolumeSound {
    private float leftVolumeSound;
    private float rightVolumeSound;

    public VolumeSound(float leftVolumeSound, float rightVolumeSound){
        this.leftVolumeSound = leftVolumeSound;
        this.rightVolumeSound = rightVolumeSound;
    }

    float getLeftVolumeSound(){
        return this.leftVolumeSound;
    }

    float getRightVolumeSound(){
        return this.rightVolumeSound;
    }
}
