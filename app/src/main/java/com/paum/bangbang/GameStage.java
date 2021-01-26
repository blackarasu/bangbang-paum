package com.paum.bangbang;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameStage {
    // game activity context
    private Context context;
    // three doors at the stage (left, middle, right)
    private Door[] doors;
    // timer for generating characters
    private CountDownTimer countDownTimer;
    // level of the stage - may be useful in the future
    private int level = 0;
    // actual player
    private Player player;
    // manages layouts at top
    private TopLayoutsManagement topLayoutsManagement;
    // index of the left door currently displayed
    private int door_index = 0;
    // initial time gap between a generation of the new characters
    private int basicGapBetweenCharacters;
    // number of displayed doors
    final private int numOfDisplayedDoors = 3;
    // number of all doors
    final private int numOfAllDoors = 9;
    private static final String TAG = "GameStage";

    public GameStage(Context context, Player player, TopLayoutsManagement topLayoutsManagement){
        this.context = context;
        this.player = player;
        this.topLayoutsManagement = topLayoutsManagement;
        this.topLayoutsManagement.initialize();
        this.topLayoutsManagement.setBorders(0);

        Resources res = this.context.getResources();
        this.basicGapBetweenCharacters = res.getInteger(R.integer.baseCharacterCreationTime);
        // initialize doors
        int[] topLayoutsId = this.topLayoutsManagement.getLayoutsId();
        IDoorObserver doorObserver = new DoorObserver(this);
        VolumeSound vs_left = new VolumeSound(0, 1);
        VolumeSound vs_middle = new VolumeSound(1, 1);
        VolumeSound vs_right = new VolumeSound(1,0);
        this.doors = new Door[numOfAllDoors];
        for(int i = 0; i < numOfAllDoors; i++){
            if(i%3 == 0)
                this.doors[i] = new Door(this.context, this.player, R.id.layoutLeft, vs_left, topLayoutsId[i], doorObserver, this.topLayoutsManagement);
            else if(i%3==1)
                this.doors[i] = new Door(this.context, this.player, R.id.layoutMiddle, vs_middle, topLayoutsId[i], doorObserver, this.topLayoutsManagement);
            else
                this.doors[i] = new Door(this.context, this.player, R.id.layoutRight, vs_right, topLayoutsId[i], doorObserver, this.topLayoutsManagement);
        }
        // starts generating characters
        generateCharacter();
    }

    void generateCharacter(){
        long gapBetweenCharacters = calculateTimeBetweenCharacters();
        // timer for generating new characters
        countDownTimer = new CountDownTimer(gapBetweenCharacters, gapBetweenCharacters) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                // get indexes of the free doors (without characters)
                List<Integer> freeDoorsIndexes = new ArrayList<>();
                for(int i = door_index; i < door_index + numOfDisplayedDoors; i++){
                    if(doors[i].getState())
                        freeDoorsIndexes.add(i);
                }
                // if not each door is occupied
                if(freeDoorsIndexes.size() > 0){
                    // draw the index
                    Random rand = new Random();
                    int index = rand.nextInt(freeDoorsIndexes.size());
                    int door_index = freeDoorsIndexes.get(index);
                    // add character for the door
                    doors[door_index].addCharacter();
                }
                // repeat process
                generateCharacter();
            }
        }.start();
    }

    // handle player shoot in the door with the specified index
    public void playerShoot(int index){
        this.doors[index + door_index].shoot();
    }

    // reset doors
    public void reset(){
        for (Door door: this.doors) {
            door.reset();
        }
    }

    public void resetStage(){
        this.door_index = 0;
        for(Door door:this.doors){
            door.hardReset();
        }
    }

    // moves to the next doors
    public void moveToNextDoors(){
        reset();
        this.door_index = (this.door_index + numOfDisplayedDoors) % numOfAllDoors;
        // displays border around new layouts
        this.topLayoutsManagement.setBorders(this.door_index);
    }

    // moves to the previous doors
    public void moveToPreviousDoors(){
        reset();
        if(this.door_index - numOfDisplayedDoors < 0)
            this.door_index = numOfAllDoors - numOfDisplayedDoors;
        else
            this.door_index = this.door_index - numOfDisplayedDoors;
        this.topLayoutsManagement.setBorders(this.door_index);
    }

    // finish the game stage
    public void finish(){
        reset();
        this.countDownTimer.cancel();
    }

    // check if all doors are accepted
    public boolean allDoorsAccepted(){
        for(Door door:this.doors){
            if(!door.isAccepted())
                return false;
        }
        return true;
    }

    // go to the next level
    public void nextLevel(){
        // pause generating new characters
        stopGeneratingCharacters();
        resetStage();
        this.level++;

        // play winning sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this.context, R.raw.win);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            // when playing is done - again start generating characters
            generateCharacter();
        });

        Log.i(TAG, "Next Level: " + this.level);
    }

    private void stopGeneratingCharacters(){
        this.countDownTimer.cancel();
    }

    // calculate time for generating new character - depends on the actual game level
    private long calculateTimeBetweenCharacters(){
        // depends on the game level
        long gapBetweenCharacters = (long)(this.basicGapBetweenCharacters - (((double)this.level/2) * 1000));
        // can't be less than 500
        if(gapBetweenCharacters <= 500)
            return 500;
        return gapBetweenCharacters;
    }
}
