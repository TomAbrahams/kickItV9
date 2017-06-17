package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getItFromOnline();
        CurrentPlayer theProfile = new CurrentPlayer();
        Player myPlayer = theProfile.profileInstance();
        loadProfile(myPlayer);


    }
    public void getItFromOnline()
    {
        final Context context = Profile.this;
        //CurrentPlayer.currentProfile.getPlayerProfile(context);
        try {
            String userId = mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();
            CurrentPlayer theCurrentPlayer = new CurrentPlayer();
            Player thePlayer = theCurrentPlayer.getCurrentProfile();
            thePlayer.retreivePlayerProfile(context);
            theCurrentPlayer.setCurrentPlayer(thePlayer);
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), "Can't find the profile.",
                    Toast.LENGTH_LONG).show();
            CurrentPlayer theCurrentPlayer = new CurrentPlayer();
            Player thePlayer = new Player();
            theCurrentPlayer.setCurrentPlayer(thePlayer);
        }
    }

    public void loadProfile(Player myProfile) {
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //filled width
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        ImageView imageViewURL = (ImageView)findViewById(R.id.profileView);

        imageViewURL.setMaxHeight(200);
        imageViewURL.setMaxWidth(200);
        String imageURL = myProfile.getPictureUrl();
        try {
            if (imageURL != "None") {
                String url = imageURL;
                ImageLoader.getInstance().displayImage(imageURL, imageViewURL);
            }
            TextView textView = (TextView) findViewById(R.id.tvProfile);
            textView.setText(myProfile.printProfile());
            //I need to get the items.
        }
        catch(Exception e)
        {

        }

    }
    public void backToMain(View v)
    {
        Intent myIntent = new Intent(Profile.this, Main_Menu.class);
        startActivity(myIntent);
    }
    public void goToEditProfile(View v)
    {
        Intent myIntent = new Intent(Profile.this, editProfile.class);
        startActivity(myIntent);
    }


}
