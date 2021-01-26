package com.paum.bangbang;

public class DoorObserver implements IDoorObserver{
    private GameStage gameStage;

    public DoorObserver(GameStage gameStage){
        this.gameStage = gameStage;
    }

    @Override
    // detects when a door is accepted
    public void singleDoorAcceptanceDetected() {
        // when all doors are accepted
        if(this.gameStage.allDoorsAccepted())
            // go to the next level
            this.gameStage.nextLevel();
    }
}
