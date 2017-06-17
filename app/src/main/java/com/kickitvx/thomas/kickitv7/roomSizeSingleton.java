package com.kickitvx.thomas.kickitv7;

import java.util.Vector;

/**
 * Created by Thomas on 5/1/2017.
 */

public class roomSizeSingleton {
    private static roomSizeSingleton instance;
    public Vector<Player> roomPlayers = new Vector<Player>();

    public static roomSizeSingleton Instance()
    {
        if(instance == null)
            instance = new roomSizeSingleton();
        return instance;
    }
    public roomSizeSingleton getInstance()
    {
        return instance;
    }
    public void setRoomPlayers(Vector<Player> thePlayers)
    {
        roomPlayers = thePlayers;
    }
    public void addPlayerToRoom(Player player)
    {
        roomPlayers.add(player);
        String world = "DA WORLD";
    }
    public static void cleaner()
    {
        instance = new roomSizeSingleton();
    }

}
