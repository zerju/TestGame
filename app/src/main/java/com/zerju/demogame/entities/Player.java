package com.zerju.demogame.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jureLaptop on 23. 01. 2017.
 */

public class Player extends RealmObject{
    @PrimaryKey
    private long id;
    private int highScore;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
