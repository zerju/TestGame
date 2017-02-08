package com.zerju.demogame;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jure on 21. 01. 2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    private SceneManager manager;


    public GamePanel(Context context,AttributeSet attrs){
        super(context,attrs);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(),this);

        manager = new SceneManager();



        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        boolean surfaceNOT = true;
        while(surfaceNOT) {
            if (holder.getSurface().isValid()) {
                thread = new MainThread(getHolder(), this);
                Constants.INIT_TIME = System.currentTimeMillis();
                thread.setRunning(true);
                thread.start();
                surfaceNOT = false;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.recieveTouch(event);

//        return super.onTouchEvent(event);
        return true;
    }

    public void update(){
        manager.update();
    }


    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        manager.draw(canvas);

    }




}
