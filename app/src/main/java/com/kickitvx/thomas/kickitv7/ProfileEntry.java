package com.kickitvx.thomas.kickitv7;

import com.kickitvx.thomas.kickitv7.Player;

/**
 * Created by Thomas on 4/30/2017.
 */

public class ProfileEntry {

        private String profileName;
        private String imageURL;

        public ProfileEntry(String profileName, String imageURL)
        {
            this.profileName = profileName;
            this.imageURL = imageURL;
        }
        public ProfileEntry(Player player)
        {
            this.profileName = player.getEmail() + " " + player.getFirstName();
            this.imageURL = player.getPictureUrl();
        }
        public String getProfileName()
        {
            return profileName;
        }
        public String getImageURL()
        {
            return imageURL;
        }
        public void setProfileName(String myProfileName)
        {
            profileName = myProfileName;
        }
        public void setImageURL(String myImageURL)
        {
            imageURL = myImageURL;
        }


}
