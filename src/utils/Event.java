package utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.techsmp.core.commands.Killboard;

public class Event implements Listener {
    boolean spec, isEventStarted;
    String sbTitle;
    Location startLoc, beginLoc;
    public Event(boolean putInSpec, Location tpLoc, Location initLoc){
        //kb = killboard;
        spec = putInSpec;
        startLoc = tpLoc;
        beginLoc = initLoc;
        //sbTitle = kbTitle;

    }
    public void startEvent(){
        /*if(kb){
            Killboard.initKillboard(sbTitle);
        }*/
        if(spec){
            for(Player p : Bukkit.getOnlinePlayers()){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec on " + p.getName());
                Teleporter.teleport(p, startLoc);
            }
        }
        isEventStarted = true;
    }
    public void endEvent(){
        if(isEventStarted) {
            isEventStarted = false;
            if (spec) {
                for(Player p : Bukkit.getOnlinePlayers()){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec off " + p.getName());
                }
            }
        }
    }
    public boolean getEventRunning(){
        return isEventStarted;
    }
    public void initPlayers(Player[] p){
        for(int i = 0; i < p.length; i++){
            Teleporter.teleport(p[i], beginLoc);
            p[i].setGameMode(GameMode.SURVIVAL);
            p[i].setHealth(20);
            p[i].setFoodLevel(20);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(isEventStarted){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec on " + e.getPlayer().getName());
        }
    }
}
