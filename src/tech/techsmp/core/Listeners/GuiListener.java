package tech.techsmp.core.Listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class GuiListener implements Listener{
	public static ArrayList<Player> inGui = new ArrayList<Player>();


    @EventHandler
	public void onInvClick(InventoryClickEvent event) {
    	if(inGui.contains(event.getWhoClicked())) {
    		event.setCancelled(true);
    	}
    
    }
    @EventHandler
	public void onInvClose(InventoryCloseEvent event) {
    	if(inGui.contains(event.getPlayer())) {
    		inGui.remove(event.getPlayer());
    	}
    
    }
}