package com.paum.bangbang;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

// class for playing a sound in a game - singleton
public class GameSoundPlayer {
    private SoundPool soundPool;
    private static GameSoundPlayer instance;
    private Context context;
    private int loadWeapon_enemyAppeared;
    private int money_clientAppeared;

    private GameSoundPlayer(Context context){
        // initialize SoundPool class
        this.context = context;
        int maxSoundStreams = this.context.getResources().getInteger(R.integer.maxSoundStreams);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().
                    setMaxStreams(maxSoundStreams)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else{
            soundPool = new SoundPool(maxSoundStreams, AudioManager.STREAM_MUSIC, 0);
        }

        // load (for now just 2) sound files
        this.loadWeapon_enemyAppeared = this.soundPool.load(context, R.raw.loadweaponenemyappeared, 1);
        this.money_clientAppeared = this.soundPool.load(context, R.raw.moneyclientappeared1, 1);

    }

    // return singleton instance
    public static GameSoundPlayer getInstance(Context context){
        if(instance == null){
            synchronized (GameSoundPlayer.class) {
                if(instance == null){
                    instance = new GameSoundPlayer(context);
                    Log.i(null, "Creates instance");
                }
            }
        }
        return instance;
    }

    // initialize a singleton - use while the game activity is setting up (a file load process can take a while)
    public static void initialize(Context context){
        if(instance == null){
            synchronized (GameSoundPlayer.class) {
                if(instance == null){
                    instance = new GameSoundPlayer(context);
                    Log.i(null, "Creates instance");
                }
            }
        }
    }

    // play sound of a good character (client) appearance
    public void goodPlayerAppeared(float leftVolume, float rightVolume){
        this.soundPool.play(money_clientAppeared, leftVolume, rightVolume, 0, 0, 1);
    }

    // play sound of bad character (thief)  appearance
    public void badPlayerAppeared(float leftVolume, float rightVolume){
        this.soundPool.play(loadWeapon_enemyAppeared, leftVolume, rightVolume, 0, 0, 1);
    }

    // release resources (probably in the future when game is over)
    public void releaseResources(){
        soundPool.release();
        soundPool = null;
    }
}
