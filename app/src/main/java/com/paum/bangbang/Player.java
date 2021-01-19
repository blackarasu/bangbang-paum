package com.paum.bangbang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Player implements ISubject{
    private int lives;
    private int score;
    private int scoreForShot;
    private List<IObserver> observers;

    public Player(int initialLives, int scoreForShot){
        this.lives = 3;
        this.score = 0;
        this.scoreForShot = scoreForShot;
        this.observers = new ArrayList<IObserver>();
    }

    public void takeLive(){

        this.lives--;
        if(this.lives <= 0)
            inform();
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
