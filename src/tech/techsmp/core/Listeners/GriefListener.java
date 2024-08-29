package tech.techsmp.core.Listeners;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GriefListener implements Listener{

    @EventHandler
	public void creeperExplode(EntityExplodeEvent e){
    	if(e.getEntityType().equals(EntityType.CREEPER)) {
    	      e.blockList().clear();
    	      Entity creeper = e.getEntity();
    	      creeper.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, creeper.getLocation(), 1);
    	}
    }
    @EventHandler
    public void sheepEatGrass(EntityChangeBlockEvent event) {
    	if(!event.getEntity().getType().equals(EntityType.VILLAGER) && event.getBlock().getType().equals(Material.FARMLAND)){
    		event.setCancelled(true);
    	}
    	/*if(event.getEntity().getType().equals(EntityType.SHEEP)) {
    		Block grass = event.getBlock();
    		
    		if(grass.getType().equals(Material.GRASS_BLOCK)) {
    			event.setCancelled(true);
    		}
    	}*/
    }
    @EventHandler
    public void cropTrample(PlayerInteractEvent event) {
    	Block crops = event.getClickedBlock();
    	if(event.getAction().equals(Action.PHYSICAL) && crops.getType().equals(Material.FARMLAND)) {
    			event.setCancelled(true);
    	}
    }
}
