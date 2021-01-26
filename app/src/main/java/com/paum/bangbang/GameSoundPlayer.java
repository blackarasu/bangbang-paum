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
    private int angryVoice_enemyKilled;
    private int wrong_shot;
    private int player_shot;
    private int machineGun_enemyNotKilled;
    private int game_start;
    private int thank_you;

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

        // play game start when it will be loaded
        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if(sampleId == game_start){
                gameStart();
            }
        });

        // load (for now just 2) sound files
        this.loadWeapon_enemyAppeared = this.soundPool.load(context, R.raw.loadweaponenemyappeared, 1);
        this.money_clientAppeared = this.soundPool.load(context, R.raw.moneyclientappeared1, 1);
        this.angryVoice_enemyKilled = this.soundPool.load(context, R.raw.angryvoiceenemykilled2, 1);
        this.wrong_shot = this.soundPool.load(context, R.raw.wrongshot, 1);
        this.player_shot = this.soundPool.load(context, R.raw.playershoot2, 1);
        this.machineGun_enemyNotKilled = this.soundPool.load(context, R.raw.machinegunenemyshoot2, 1);
        this.thank_you = this.soundPool.load(context, R.raw.thankyou, 1);
        this.game_start = this.soundPool.load(context, R.raw.bellgamestart, 1);
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
    public void goodCharacterAppeared(VolumeSound volumeSound){
        this.soundPool.play(money_clientAppeared, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // play sound of bad character (thief)  appearance
    public void badCharacterAppeared(VolumeSound volumeSound){
        this.soundPool.play(loadWeapon_enemyAppeared, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // play sound of killing a bad character
    public void badCharacterKilled(VolumeSound volumeSound){
        this.soundPool.play(this.angryVoice_enemyKilled, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // play sound of a wrong shot
    public void wrongShot(VolumeSound volumeSound){
        this.soundPool.play(this.wrong_shot, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // play player shot
    public void playerShot(VolumeSound volumeSound){
        this.soundPool.play(this.player_shot, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // bad character did not die
    public void badCharacterNotKilled(VolumeSound volumeSound){
        this.soundPool.play(this.machineGun_enemyNotKilled, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // play game start signal
    public void gameStart(){
        this.soundPool.play(this.game_start, 1, 1, 0, 0, 1);
    }

    public void goodCharacterNotKilled(VolumeSound volumeSound){
        this.soundPool.play(this.thank_you, volumeSound.getLeftVolumeSound(), volumeSound.getRightVolumeSound(), 0, 0, 1);
    }

    // release resources (probably in the future when game is over)
    public void releaseResources(){
        soundPool.release();
        soundPool = null;
    }
}
