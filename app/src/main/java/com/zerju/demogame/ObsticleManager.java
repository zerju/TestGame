package com.zerju.demogame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Jure on 21. 01. 2017.
 */

public class ObsticleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obsticle> obsticles;
    private int playerGap;
    private int obsticleGap;
    private int obsticleHeight;
    private int color;


    private long startTime;
    private long initTime;

    public static int score = 0;

    public ObsticleManager(int playerGap, int obsticleGap, int obsticleHeight, int color){
        this.playerGap = playerGap;
        this.obsticleGap = obsticleGap;
        this.obsticleHeight = obsticleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();
        obsticles = new ArrayList<>();

        populateObsticles();
    }

    private void populateObsticles(){
        int curY = -5*Constants.SCREEN_HEIGHT/4;
        while (curY < 0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obsticles.add(new Obsticle(obsticleHeight, color, xStart, curY, playerGap));
            curY += obsticleHeight + obsticleGap;
        }
    }

    public boolean playerCollide(RectPlayer player){
        for(Obsticle ob:obsticles){
            if(ob.playerColide(player)){
                return true;
            }
        }
        return false;
    }

    public void playerPointUp(RectPlayer player){
        for(Obsticle ob: obsticles){
            if(ob.pointUp(player)){
                score++;
            }
        }
    }

    public  void update(){
        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/2000.0))*Constants.SCREEN_HEIGHT/(10000.0f);
        for(Obsticle ob:obsticles){
            ob.incrementY(speed* elapsedTime);

        }

        if(obsticles.get(obsticles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obsticles.add(0, new Obsticle(obsticleHeight, color, xStart, obsticles.get(0).getRectangle().top - obsticleHeight - obsticleGap, playerGap));
            obsticles.remove(obsticles.size() - 1);
            //score++;
        }

    }

    public void draw(Canvas canvas){
        for(Obsticle ob:obsticles){
            ob.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText(String.valueOf(score),50,50 + paint.descent() - paint.ascent(), paint);
    }
}
