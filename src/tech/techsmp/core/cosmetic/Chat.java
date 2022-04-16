package tech.techsmp.core.cosmetic;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;


public class Chat implements Listener{
	public static ArrayList<String> Muted= new ArrayList<String>();

        
    @EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {

        String message = e.getMessage().replaceAll("%", "%%");
        Player p = e.getPlayer();
    	if(Muted.contains(p.getUniqueId().toString())){
    		p.sendMessage("§cSorry! You are muted and cannot talk in chat.");
            Logger.getLogger("Minecraft").info("[Muted] " + p.getName() +" " + e.getMessage());
            e.setCancelled(true);
            return;
    	}
        if(p.hasPermission("rank.owner") && p.hasPermission("rank.owner")){
            e.setFormat("§8[§cOwner§8] §f" + p.getName().toString() + " » " + message);
        }
        else if(p.hasPermission("rank.admin")){
            e.setFormat("§8[§6Admin§8] §f" + p.getName().toString() + " » " + message);
        }
        else if(p.hasPermission("rank.trusted")){
            e.setFormat("§e§l✩§f §r" + p.getName().toString() + " » " + message);
        }
        else if(p.isWhitelisted()){
            e.setFormat(p.getName().toString() + " » " + e.getMessage());
        }
        else{
            e.setFormat("§8[Guest]§7§o" + p.getName().toString() + " » §r" + message);

        }
    }
}