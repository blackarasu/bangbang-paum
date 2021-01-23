package com.paum.bangbang;

// observes when a player lose one life
public class LoseOneLifeObserver implements ILoseOneLifeObserver {

    private GameStage gameStage;

    public LoseOneLifeObserver(GameStage gameStage){
        this.gameStage = gameStage;
    }

    // action to be taken when a player loses a life
    @Override
    public void lostOneLifeDetected() {
        gameStage.reset();
    }
}
