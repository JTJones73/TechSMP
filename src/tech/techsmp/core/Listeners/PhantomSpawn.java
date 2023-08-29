package tech.techsmp.core.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PhantomSpawn implements Listener{
	static int numSleeping = 0;
	static int numSpecs = 0;
    @EventHandler
	public void phantomSpawn(EntitySpawnEvent e){
    	if(e.getEntity() instanceof Phantom) {
    		e.setCancelled(true);
    	}
}

}