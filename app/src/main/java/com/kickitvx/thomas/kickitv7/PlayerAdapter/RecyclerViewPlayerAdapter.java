package com.kickitvx.thomas.kickitv7.PlayerAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.kickitvx.thomas.kickitv7.Player;
import com.kickitvx.thomas.kickitv7.ProfileEntry;
import com.kickitvx.thomas.kickitv7.R;
import com.kickitvx.thomas.kickitv7.roomSizeSingleton;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Vector;

/**
 * Created by Thomas on 4/30/2017.
 */

public class RecyclerViewPlayerAdapter extends RecyclerView.Adapter<RecyclerViewPlayerAdapter.ViewHolder> {
    private Context context;
    private Vector<ProfileEntry> profiles;
    final Vector<Player> PlayersInRoom = new Vector<Player>();

    //Variables
    Vector<Player>listPlayersDB = new Vector<Player>();
    String[] profileNames;
    String[] imageURLs;
    int screenWidth;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public RecyclerViewPlayerAdapter(Vector<ProfileEntry> profiles, Context context)
    {
        //getRoomMates();
        this.context = context;
        this.profiles = profiles;

    }
    @Override
    public RecyclerViewPlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //Here is the settings.
        //Get teh settings.
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
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        ImageView imageViewURL = viewHolder.imgViewIcon;

        imageViewURL.setMinimumHeight(100);
        imageViewURL.setMinimumWidth(100);
        String imageURL = profiles.get(position).getImageURL();

        if (imageURL != "None") {
            String url = imageURL;
            ImageLoader.getInstance().displayImage(imageURL, imageViewURL);
        }


        viewHolder.txtViewTitle.setText(profiles.get(position).getProfileName());
        //Need to make this an image holder.
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View profileLayoutView) {
            super(profileLayoutView);

            txtViewTitle = (TextView) profileLayoutView.findViewById(R.id.tvProfileName);
            imgViewIcon = (ImageView) profileLayoutView.findViewById(R.id.imgViewIcon);
        }
    }


    @Override
    public int getItemCount() {
        profiles = new Vector<>();

        listPlayersDB = roomSizeSingleton.Instance().getInstance().roomPlayers;
        if(listPlayersDB.size() > 0) {
            for (int idx = 0; idx < listPlayersDB.size(); idx++) {
                Player player = listPlayersDB.get(idx);
                ProfileEntry profileEntry = new ProfileEntry(player);

                profiles.add(profileEntry);
            }
            int number = profiles.size();

        }
        return profiles.size();
    }


}
