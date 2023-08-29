package tech.techsmp.core.cosmetic;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;


public class Chat implements Listener{
	public static ArrayList<String> Muted= new ArrayList<String>();

        
    @EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {

        String message = e.getMessage().replaceAll("%", "%%");
        Player p = e.getPlayer();
    	if(Muted.contains(p.getName().toLowerCase())){
    		p.sendMessage("§cSorry! You are muted and cannot talk in chat.");
            Bukkit.getLogger().info("[Muted] " + p.getName() +": " + e.getMessage());
            e.setCancelled(true);
            return;
    	}
        if(p.hasPermission("rank.owner") && p.hasPermission("rank.owner")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage("§8[§cOwner§8] §f" + p.getName().toString() + " » " + message);
            }
            Bukkit.getLogger().info("§8[§cOwner§8] §f" + p.getName().toString() + " » " + message);

        }
        else if(p.hasPermission("rank.admin")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage("§8[§6Admin§8] §f" + p.getName().toString() + " » " + message);
            }
            Bukkit.getLogger().info("§8[§6Admin§8] §f" + p.getName().toString() + " » " + message);

        }
        else if(p.hasPermission("rank.trusted")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage("§e§l✩§f §r" + p.getName().toString() + " » " + message);
            }
            Bukkit.getLogger().info("§e§l✩§f §r" + p.getName().toString() + " » " + message);

        }
        else if(p.isWhitelisted()){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(p.getName().toString() + " » " + e.getMessage());
            }
            Bukkit.getLogger().info(p.getName().toString() + " » " + e.getMessage());

        }
        else{
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage("§8[Guest]§7§o" + p.getName().toString() + " » §r" + message);
            }
            Bukkit.getLogger().info("§8[Guest]§7§o" + p.getName().toString() + " » §r" + message);


        }
    }
}