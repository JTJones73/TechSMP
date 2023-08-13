package tech.techsmp.core.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;


public class EntityHurtListener implements Listener {
	@EventHandler
	public void BeeDamaged(EntityDamageEvent event) {
		if(event.getEntityType() == EntityType.BEE) {
			if(event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
				Entity damagedEntity = event.getEntity();
				Block blockAtDamageLocation = damagedEntity.getWorld().getBlockAt(damagedEntity.getLocation());
				if(blockAtDamageLocation.getType() == Material.BEEHIVE || blockAtDamageLocation.getType() == Material.BEE_NEST) {
					event.setCancelled(true);
				}
			}
		}
	}
}