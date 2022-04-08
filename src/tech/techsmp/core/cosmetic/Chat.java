package tech.techsmp.core.cosmetic;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;


public class Chat implements Listener{
        
    @EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        if(p.hasPermission("rank.owner") && p.hasPermission("rank.owner")){
            e.setFormat("§8[§cOwner§8] §f" + p.getName().toString() + " » " + e.getMessage());
        }
        else if(p.hasPermission("rank.admin")){
            e.setFormat("§8[§6Admin§8] §f" + p.getName().toString() + " » " + e.getMessage());
        }
        else if(p.hasPermission("rank.trusted")){
            e.setFormat("§e§l✩§f §r" + p.getName().toString() + " » " + e.getMessage());
        }
        else if(p.isWhitelisted()){
            e.setFormat(p.getName().toString() + " » " + e.getMessage());
        }
        else{
            e.setFormat("§8[Guest]§7§o" + p.getName().toString() + " » §r" + e.getMessage());

        }
    }
}