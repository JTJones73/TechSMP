package utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tech.techsmp.core.Main;

import java.lang.annotation.Documented;
import java.util.LinkedList;

public class Teleporter implements Listener {
    public static LinkedList<Player> playerList = new LinkedList<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof  Player && playerList.contains((Player) event.getEntity())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDoDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && playerList.contains((Player) event.getDamager())){
            event.setCancelled(true);
        }
    }
    public static boolean teleport(Player p, Location loc){
        p.teleport(loc);
        new BukkitRunnable() {
            @Override
            public void run() {
                playerList.remove(p);
            }
        }.runTaskLater(Main.getInstance(),3);
        return true;
    }
}
