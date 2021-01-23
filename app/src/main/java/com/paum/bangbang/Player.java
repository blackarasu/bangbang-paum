package com.paum.bangbang;

import java.util.ArrayList;
import java.util.List;

public class Player implements ISubject{
    private int lives;
    private int score;
    private int scoreForShot;
    // observers - activates when a player has no more lives
    private List<IObserver> observers;
    // observer - activates when a player lose a single life
    private ILoseOneLifeObserver lostLiveObserver;

    public Player(int initialLives, int scoreForShot){
        this.lives = 3;
        this.score = 0;
        this.scoreForShot = scoreForShot;
        this.observers = new ArrayList<IObserver>();
    }

    // takes player's life
    public void takeLive(){
        this.lives--;
        if(lostLiveObserver != null)
            lostLiveObserver.lostOneLifeDetected(); // informs an observer about losing a life
        if(this.lives <= 0)
            inform();   // informs observers about lack of lives
    }

    public void addScore(){
        this.score += this.scoreForShot;
    }

    public int getScore(){
        return this.score;
    }

    public int getLives(){
        return this.lives;
    }

    public void attachLoseOneLifeObserver(ILoseOneLifeObserver observer){
        this.lostLiveObserver = observer;
    }

    @Override
    public void attach(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void inform() {
        for(IObserver observer : this.observers){
            observer.update();
        }
    }
}
