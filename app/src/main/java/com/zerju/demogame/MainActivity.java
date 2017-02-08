package com.zerju.demogame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zerju.demogame.entities.Player;

import java.io.IOException;

import io.realm.Realm;

public class MainActivity extends Activity {

    private TextView finalScore;
    private Button playAgain;
    private Button mainMenu;
    private TextView highScoreText;
    SoundPool soundPool;
    int soundID;
    MediaPlayer mediaPlayer;
    int length;
    public static boolean needsReset = false;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.big_boss_2_0);

       // try {
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
       // }catch (IOException e){e.printStackTrace();}
        GameplayScene.mainContext = getBaseContext();
        GameplayScene.updateActivity(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        finalScore = (TextView)findViewById(R.id.yourScoreText);
        playAgain = (Button)findViewById(R.id.btnPlayAgain);
        mainMenu = (Button)findViewById(R.id.btnMainMenu);
        highScoreText = (TextView)findViewById(R.id.highScoreText);
        System.out.println(finalScore);



        //GameOverActivity.context = getApplicationContext();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        length=mediaPlayer.getCurrentPosition();
    }

    public void getGameOver(){
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (GameplayScene.gameOver){
                            realm.beginTransaction();
                            Player player = realm.where(Player.class).findFirst();
                            if (player == null) {

                                Player player1 = new Player();

                                player1.setHighScore(ObsticleManager.score);
                                player1.setId(1);
                                realm.createObject(Player.class, 1);

                            } else {
                                if (player.getHighScore() < ObsticleManager.score) {
                                    Player player1 = new Player();
                                    player1.setId(1);
                                    int highScore = ObsticleManager.score;
                                    player1.setHighScore(highScore);
                                    realm.copyToRealmOrUpdate(player1);

                                }
                            }

                            int highScore = player.getHighScore();
                            findViewById(R.id.gamePanelMain).setVisibility(View.GONE);
                            findViewById(R.id.gameOverMain).setVisibility(View.VISIBLE);
                            realm.commitTransaction();
                            finalScore.setText(String.valueOf(ObsticleManager.score));
                            highScoreText.setText(String.valueOf(highScore));
                            playAgain.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    findViewById(R.id.gamePanelMain).setVisibility(View.VISIBLE);
                                    findViewById(R.id.gameOverMain).setVisibility(View.GONE);
                                    findViewById(R.id.gamePanelMain).invalidate();
                                    GameplayScene.gameOver = false;
                                    needsReset = true;
                                }
                            });

                            mainMenu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GameplayScene.gameOver = false;
                                    findViewById(R.id.gamePanelMain).invalidate();
                                    finish();
                                    //       startActivity(new Intent(MainActivity.this,Main2Activity.class));
                                }
                            });
                            ObsticleManager.score = 0;
                        }
                    }
                });
            }
        }.start();

    }
}
