package com.zerju.demogame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Jure on 21. 01. 2017.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animationManager;

    public RectPlayer(Rect rectange, int color){
        this.setRectangle(rectange);
        this.setColor(color);

        //code set for animating player with sprite sheets
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue_walk1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue_walk2);

        idle = new Animation(new Bitmap[]{idleImg},2);
        walkRight = new Animation(new Bitmap[]{walk1},0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1,walk2},0.5f);

        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getColor());
        canvas.drawRect(getRectangle(),paint);//if we want static player
       // animationManager.draw(canvas,rectangle);//if we want animated player
    }

    @Override
    public void update() {
        animationManager.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;
        //left,top,right,bottom
        rectangle.set(point.x - getRectangle().width()/2,point.y - getRectangle().height()/2,point.x + getRectangle().width()/2,point.y + getRectangle().height()/2);

        //state 0 = idle, state 1 = right, state 2 = left
        int state = 0;
        if(rectangle.left - oldLeft > 5){
            state = 1;
        }else if(rectangle.left - oldLeft < -5){
            state = 2;
        }
        animationManager.playAnim(state);
        animationManager.update();
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
