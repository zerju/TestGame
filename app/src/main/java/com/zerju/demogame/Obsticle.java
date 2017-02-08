package com.zerju.demogame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by jureLaptop on 8. 02. 2017.
 */
public class Obsticle implements GameObject{

    private Rect rectangle3;
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public Obsticle(int rectHeight, int color, int startX, int startY, int playerGap){
        this.color = color;
        rectangle = new Rect(0,startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
        rectangle3 = new Rect(startX,startY,startX + playerGap,startY + rectHeight);
        //new Rect(left,top,right,bottom)
    }

    public boolean playerColide(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangle()) || Rect.intersects(rectangle2,player.getRectangle());
    }

    public boolean pointUp(RectPlayer player){
        if(player.getRectangle().intersects(rectangle3.left,rectangle3.top,rectangle3.right,rectangle3.bottom)){
            rectangle3.setEmpty();
            rectangle3.set(0,0,0,0);
            return true;
        }
        return false;
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;

        rectangle3.top += y;
        rectangle3.bottom += y;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        Paint paintTransperent = new Paint();
        paintTransperent.setColor(Color.TRANSPARENT);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
        canvas.drawRect(rectangle3,paintTransperent);
    }

    @Override
    public void update() {

    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }
}
