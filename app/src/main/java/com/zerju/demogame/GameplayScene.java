package com.zerju.demogame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by Jure on 21. 01. 2017.
 */

public class GameplayScene implements Scene {

    private Rect r = new Rect();

    public static Context mainContext;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
   // private SceneManager sm = new SceneManager();

    private boolean movingPlayer = false;

    public static boolean gameOver = false;
    private long gameOverTime;

    public static WeakReference<Activity> mActivityRef;
    public static void updateActivity(Activity activity) {
        mActivityRef = new WeakReference<Activity>(activity);
    }

    public GameplayScene(){
        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
    }



    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

        movingPlayer = false;
    }


    @Override
    public void update() {
        if(MainActivity.needsReset){
            reset();
            MainActivity.needsReset = false;
        }
        if(!gameOver) {
            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
                reset();
            }else {
                obstacleManager.playerPointUp(player);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver){
           /* Activity activity = mActivityRef.get();
            MainActivity mainActivity = (MainActivity)activity;
            mainActivity.getGameOver(activity.getApplicationContext());
           // MainActivity.getGameOver(activity.getApplicationContext());
            MainActivity.gameOver = true;*/
           // GamePanel mainActivity = new GamePanel(mainContext);
           // SceneManager.ACTIVE_SCENE = 1;
            //View gameOverView = LayoutInflater.from(mActivityRef.get().getApplicationContext()).inflate(R.layout.activity_game_over, null, false);
            MainActivity activity = (MainActivity)mActivityRef.get();
            ((MainActivity) mActivityRef.get()).getGameOver();
            //mActivityRef.get().findViewById(R.id.gamePanelMain).setVisibility(View.GONE);
           // mActivityRef.get().findViewById(R.id.gameOverMain).setVisibility(View.VISIBLE);
            //sm.update();
            /*Intent intent = new Intent(mainContext, GameOverActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainContext.startActivity(intent);*/

            /*Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas,paint,"GAME OVER");*/
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY())){
                    movingPlayer = true;
                }
                if(gameOver && System.currentTimeMillis()-gameOverTime >= 2000){ //game over screen
                    // pride tu v poštev
                   // reset();
                   // gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;

        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
