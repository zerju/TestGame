package com.zerju.demogame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by Jure on 11. 02. 2017.
 *
 * Not worth using. Won't use until further notice.
 */

public class Trail implements Scene{
    private Path drawPath;
    private float startX;
    private float startY;

    public Trail(float x, float y){
        drawPath = new Path();
        startX = x;
        startY = y;
    }

    @Override
    public void update() {

    }

    public void update(float x,float y){
        //drawPath.reset();

        drawPath.rLineTo(x,y+50);
        drawPath.setLastPoint(x,y+50);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        //drawPath.rMoveTo(startX,startY+50);
        //drawPath.rMoveTo(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT);
        canvas.drawPath(drawPath,paint);
    }

    @Override
    public void terminate() {
        drawPath.reset();
    }

    @Override
    public void recieveTouch(MotionEvent event) {

    }
}
