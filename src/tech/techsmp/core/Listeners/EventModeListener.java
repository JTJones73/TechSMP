package tech.techsmp.core.Listeners;


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
import utils.ConfigMessage;
import utils.EventMode;
import utils.InvSave;

import java.util.ArrayList;

public class EventModeListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(EventMode.isEventStarted && EventMode.spec){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec on " + e.getPlayer().getName());
        }
        else if(EventMode.isEventStarted){
            try{
                InvSave.loadInv(e.getPlayer(), e.getPlayer().getUniqueId().toString() + "|EVENT");
            }
            catch (Exception ex){}
        }
    }
    @EventHandler
    public void onTeleportCommand(PlayerCommandPreprocessEvent e){
        if(EventMode.isEventStarted && EventMode.restrictCmds && !e.getPlayer().hasPermission("rank.trusted")){
            String[] command = e.getMessage().toLowerCase().replaceAll("techsmp:", "").split(" ");
            if(EventMode.forbiddenCmds.contains(command[0])) {
                e.getPlayer().sendMessage(ConfigMessage.getMessage("EVENTMODE_FORBIDDEN_CMD", new String[]{" "}));
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (EventMode.imortalEnabled) {
            if(e.getEntity() instanceof Player){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if (EventMode.imortalEnabled) {
            if(e.getEntity() instanceof Player){
                e.setCancelled(true);
            }
        }
    }
}
