package tech.techsmp.core.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SpawnProtection implements Listener{
	
	
	@EventHandler                        
    public void onBlockBreak(BlockBreakEvent event){
		Block b = event.getBlock();
		if(!event.getPlayer().hasPermission("rank.trusted")) {
		if(b.getLocation().getBlockX() <-87 &&b.getLocation().getBlockX() >-107 && b.getLocation().getBlockZ() < 12 && 
				b.getLocation().getBlockZ() >-12 && b.getLocation().getBlockY() <102 && b.getLocation().getBlockY() >-81) {
			if(!(b.getType().equals(Material.FILLED_MAP))) {
				event.setCancelled(true);
			}
		}
		}
	}
	@EventHandler                        
    public void onBlockPlace(BlockPlaceEvent event){
		Block b = event.getBlock();
		if(!event.getPlayer().hasPermission("rank.trusted")) {
		if(b.getLocation().getBlockX() <-87 &&b.getLocation().getBlockX() >-107 && b.getLocation().getBlockZ() < 12 && 
				b.getLocation().getBlockZ() >-12 && b.getLocation().getBlockY() <102 && b.getLocation().getBlockY() >-81) {
			if(!(b.getType().equals(Material.FILLED_MAP))) {
				event.setCancelled(true);
			}
		}
		}
	}

}
