package com.zerju.demogame;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Jure on 21. 01. 2017.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
