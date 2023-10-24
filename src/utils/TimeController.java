package utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tech.techsmp.core.Main;

import java.util.logging.Logger;

public class TimeController implements Listener {
    static boolean running = true;
    static int numPeriods = 0;

    public static void timerTask() {
        Bukkit.getConsoleSender().sendMessage("DEBUG TimeController " + ChatColor.AQUA + "timer Started");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (running && numPeriods >= 1000) {
                    if (Bukkit.getOnlinePlayers().isEmpty()) {
                        Bukkit.getWorld("World").setTime((int)(Bukkit.getWorld("World").getTime() / 24000) * 24000 + 24000);
                        Bukkit.getConsoleSender().sendMessage("Set Time to Day");
                        Bukkit.getWorld("World").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

                        cancel();
                    }
                } else if (running) {

                    numPeriods++;
                    if(numPeriods == 1)
                        Bukkit.getConsoleSender().sendMessage("Timer Running");


                }
                else{
                    Bukkit.getConsoleSender().sendMessage("Timer Cancelled");

                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 24);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try{
            Bukkit.getWorld("World").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

            running = false;
            numPeriods = 0;
        }
        catch(Exception e){};
    }
    @EventHandler
    public void onServerStart(WorldInitEvent e){

        timerTask();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Bukkit.getOnlinePlayers().size() <= 1) {
            Bukkit.getWorld("World").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            if(!running){
                timerTask();
            }
            running = true;
            numPeriods = 0;
        }
        else{
            Bukkit.getConsoleSender().sendMessage("Daylight savings time DEBUG: Not empty");

        }
    }
}