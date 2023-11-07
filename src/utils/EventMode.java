package utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class EventMode implements Listener {
    public static boolean restrictCmds = true;
    public static boolean spec, isEventStarted, imortalEnabled;
    String sbTitle;
    Location startLoc, beginLoc;
    public static ArrayList<String> forbiddenCmds = new ArrayList<>(10);
    public static ArrayList<Player> activePlayer = new ArrayList<>(10);

    public EventMode(boolean putInSpec, Location tpLoc, Location initLoc){
        //kb = killboard;
        spec = putInSpec;
        startLoc = tpLoc;
        beginLoc = initLoc;
        forbiddenCmds.add("/tpa");
        forbiddenCmds.add("/spleef");
        forbiddenCmds.add("/parkour");
        forbiddenCmds.add("/bedtp");
        forbiddenCmds.add("/home");
        forbiddenCmds.add("/spawn");

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
    public void setImortalEnabled(boolean imortal){
        imortalEnabled = imortal;
    }
    public boolean getEventRunning(){
        return isEventStarted;
    }
    public void initPlayers(Player[] p){
        for(int i = 0; i < p.length; i++){
            Teleporter.teleport(p[i], beginLoc);
            //InvSave.delInvKey(p[i].getUniqueId() + "|EVENT");
            InvSave.saveInv(p[i], p[i].getUniqueId().toString() + "|EVENT");
            p[i].getInventory().clear();
            p[i].getInventory().setArmorContents(null);
            p[i].setGameMode(GameMode.SURVIVAL);
            p[i].setHealth(20);
            p[i].setFoodLevel(20);
            p[i].setSaturation(20);
            activePlayer.add(p[i]);
        }
    }
    public boolean unInit(Player[] p){
        for(int i = 0; i < p.length; i++){
            try {
                activePlayer.remove(p[i]);
            }catch (Exception e){
                return false;
            }
        }
        for(int i = 0; i < p.length; i++){
            p[i].setGameMode(GameMode.SPECTATOR);
            InvSave.loadInv(p[i], p[i].getUniqueId() + "|EVENT");
            InvSave.delInvKey(p[i].getUniqueId() + "|EVENT");
        }
        return true;
    }
    public void permitCmd(String permit){
        forbiddenCmds.remove(permit);
    }
    public void restrictCmd(String restrict){
        forbiddenCmds.add(restrict);
    }
}

