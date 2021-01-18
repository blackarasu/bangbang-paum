package com.paum.bangbang;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameStage {
    // game activity context
    private Context context;
    // three doors at the stage (left, middle, right)
    private Door[] doors;
    // number of doors
    final private int numOfDoors = 3;
    // timer for generating characters
    private CountDownTimer countDownTimer;
    // level of the stage - may be useful in the future
    private int level = 0;

    public GameStage(Context context){
        this.context = context;
        // initialize doors - pass context, layout id, left and right channel volume for a sound
        this.doors = new Door[numOfDoors];
        doors[0] = new Door(this.context, R.id.layoutLeft, 0, 1);
        doors[1] = new Door(this.context, R.id.layoutMiddle, 1, 1);
        doors[2] = new Door(this.context, R.id.layoutRight, 1, 1);
        // starts generating characters
        generateCharacter();
    }

    void generateCharacter(){
        // for now - generate character every 3000ms
        countDownTimer = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                // get indexes of the free doors (without characters)
                List<Integer> freeDoorsIndexes = new ArrayList<>();
                for(int i = 0; i < numOfDoors; i++){
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
    public void playerShoot(Player player, int index){
        this.doors[index].shoot(player);
    }
}
