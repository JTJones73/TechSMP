/*
*   Author:         MeType
*   Description:    Prevents bees from suffocating
* */
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
                    event.setCancelled(true);
            }
        }
    }
} 