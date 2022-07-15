package tech.techsmp.core.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerTeleport implements Listener{
	
	public static Map<String, Location> playerLastTpByUUID = new HashMap<String, Location>(10);
	
	@EventHandler                        
    public void onPlayerTeleport(PlayerTeleportEvent event){
		Player p = event.getPlayer();
		playerLastTpByUUID.put(p.getUniqueId().toString(), event.getFrom());
	}

}
