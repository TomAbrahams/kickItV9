package com.kickitvx.thomas.kickitv7;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        //MediaController mediaController = new MediaController(this);
        // mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.giphy);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        pressStart();
    }
    public void pressStart()
    {
        MyTextView button = (MyTextView)findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                Intent myIntent = new Intent(splashscreen.this, sign_in.class);
                startActivity(myIntent);


            }
        });

    }
    @Override
    public void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_splashscreen);
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.giphy);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        pressStart();
    }
}
