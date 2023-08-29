package tech.techsmp.core.Listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
public class EnderManGreif implements Listener{
    @EventHandler
	public void phantomSpawn(EntityChangeBlockEvent e){
    	if(e.getEntity() instanceof Enderman) {
    		e.setCancelled(true);
    	}
}

}