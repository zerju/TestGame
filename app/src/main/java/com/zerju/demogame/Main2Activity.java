package com.zerju.demogame;

import android.app.FragmentTransaction;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityOptionsCompat;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private Button playButton;
    MediaPlayer mediaPlayer;
    private Button settingsButton;
    int length;
    public static Drawable wallpaperDrawable;
    public static Bitmap wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);


        playButton = (Button) findViewById(R.id.playButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        PackageManager pm = getApplicationContext().getPackageManager();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        if(wallpaperManager.getWallpaperInfo() != null){ //check if this is live wallpaper
            wallpaperDrawable = wallpaperManager.getWallpaperInfo().loadThumbnail(pm);
        }else {// else it's a static wallpaper
            wallpaperDrawable = wallpaperManager.getDrawable();
        }

        final View mainView = findViewById(R.id.activity_main2);
        mainView.post(new Runnable() {//set wallpaper as background of activity

            @Override
            public void run() {
                mainView.setBackground(wallpaperDrawable);
            }
        });


        final Intent intent = new Intent(this,MainActivity.class);//intent for fade_in and fade_out

        //set background music for main menu
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        final Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();//bundle animation
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent, bundle);//start new activity with animation
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        length=mediaPlayer.getCurrentPosition();//save where the music paused

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }
}
