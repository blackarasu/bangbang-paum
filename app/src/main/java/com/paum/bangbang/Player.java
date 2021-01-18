package com.paum.bangbang;

public class Player {
    private int lives;
    private int score;

    public Player(){
        this.lives = R.integer.playerInitialLives;
        this.score = 0;
    }

    public void takeLive(){
        this.lives--;
    }

    public void addScore(){
        this.score += R.integer.scoreForKillBadCharacter;
    }
}
